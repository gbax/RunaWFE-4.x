package ru.runa.gpd;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.PlatformUI;

import ru.runa.gpd.lang.model.BotTask;
import ru.runa.gpd.util.BotTaskUtils;
import ru.runa.gpd.util.IOUtils;

import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;

/**
 * 
 * Currently uniqueness of bot name (globally) is required.
 * 
 * @author Dofs
 * @since 3.6
 */
public class BotCache {
    private static final Map<String, Set<String>> BOT_STATION_BOTS = Maps.newHashMap();
    private static final Map<String, List<BotTask>> BOT_TASKS = Maps.newHashMap();
    private static final Map<BotTask, IFile> BOT_TASK_FILES = Maps.newHashMap();
    static {
        reload();
    }

    public static synchronized void reload() {
        try {
            BOT_STATION_BOTS.clear();
            BOT_TASKS.clear();
            BOT_TASK_FILES.clear();
            IProject[] projects = IOUtils.getAllBotStationProjects();
            for (IProject botStationProject : projects) {
                Set<String> botNames = Sets.newHashSet();
                IFolder botStationFolder = botStationProject.getFolder("src/botstation");
                for (IResource botResource : botStationFolder.members()) {
                    if (botResource instanceof IFolder) {
                        IFolder botFolder = (IFolder) botResource;
                        String botName = botFolder.getName();
                        botNames.add(botName);
                        List<BotTask> botTasks = Lists.newArrayList();
                        for (IResource taskResource : botFolder.members()) {
                            if (taskResource instanceof IFile && taskResource.getFileExtension() == null) {
                                IFile botTaskFile = (IFile) taskResource;
                                cacheBotTask(botTaskFile, botTasks);
                            }
                        }
                        BOT_TASKS.put(botName, botTasks);
                    }
                }
                BOT_STATION_BOTS.put(botStationProject.getName(), botNames);
            }
        } catch (final Throwable th) {
            try {
                PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        PluginLogger.logError(Localization.getString("BotCache.unabletoload"), th);
                    }
                });
            } catch (Exception e) {
                PluginLogger.logErrorWithoutDialog("BotCache.unabletoload", e);
            }
        }
    }

    private static void cacheBotTask(IFile botTaskFile, List<BotTask> botTasks) throws CoreException, IOException {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(botTaskFile.getContents(), Charsets.UTF_8);
            List<String> lines = CharStreams.readLines(reader);
            String configurationFileData = "";
            if (lines.size() > 1) {
                String configurationFileName = lines.get(1);
                if (!Strings.isNullOrEmpty(configurationFileName)) {
                    IFile confFile = ((IFolder) botTaskFile.getParent()).getFile(configurationFileName);
                    if (confFile.exists()) {
                        configurationFileData = IOUtils.readStream(confFile.getContents());
                    }
                }
            }
            BotTask botTask = BotTaskUtils.createBotTask(botTaskFile.getName(), lines.get(0), configurationFileData);
            botTasks.add(botTask);
            BOT_TASK_FILES.put(botTask, botTaskFile);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static synchronized void invalidateBotTask(IFile botTaskFile, BotTask botTask) throws CoreException, IOException {
        String botName = botTaskFile.getParent().getName();
        List<BotTask> botTasks = BOT_TASKS.get(botName);
        botTasks.remove(botTask);
        BOT_TASK_FILES.remove(botTask);
        cacheBotTask(botTaskFile, botTasks);
    }

    /**
     * Notify cache about new bot task.
     */
    public static synchronized void newBotTaskHasBeenCreated(String botName, IFile botTaskFile, BotTask botTask) {
        BOT_TASKS.get(botName).add(botTask);
        BOT_TASK_FILES.put(botTask, botTaskFile);
    }

    /**
     * Gets all bot stations.
     * @return not <code>null</code>
     */
    public static synchronized List<String> getAllBotStationNames() {
        List<String> botStationNames = Lists.newArrayList(BOT_STATION_BOTS.keySet());
        Collections.sort(botStationNames);
        return botStationNames;
    }

    /**
     * Gets all bots.
     * @return not <code>null</code>
     */
    public static synchronized List<String> getAllBotNames() {
        List<String> botNames = Lists.newArrayList(BOT_TASKS.keySet());
        Collections.sort(botNames);
        return botNames;
    }

    /**
     * Gets bot tasks by bot.
     * @return bot tasks, not <code>null</code>
     */
    public static synchronized List<BotTask> getBotTasks(String botName) {
        List<BotTask> botTasks = Lists.newArrayList();
        if (BOT_TASKS.containsKey(botName)) {
            botTasks.addAll(BOT_TASKS.get(botName));
            Collections.sort(botTasks);
        }
        return botTasks;
    }

    /**
     * Gets bot task by bot and name.
     * @return bot task or <code>null</code>
     */
    public static synchronized BotTask getBotTask(String botName, String botTaskName) {
        if (BOT_TASKS.containsKey(botName)) {
            for (BotTask botTask : BOT_TASKS.get(botName)) {
                if (Objects.equal(botTaskName, botTask.getName())) {
                    return botTask;
                }
            }
        }
        return null;
    }

    /**
     * Gets bot task by bot and name.
     */
    public static synchronized BotTask getBotTaskNotNull(String botName, String botTaskName) {
        BotTask botTask = getBotTask(botName, botTaskName);
        if (botTask == null) {
            throw new RuntimeException("bot task not found by name '" + botTaskName + "' in '" + botName + "'");
        }
        return botTask;
    }

    /**
     * Gets bot task by file.
     */
    public static synchronized BotTask getBotTask(IFile botTaskFile) {
        for (Map.Entry<BotTask, IFile> entry : BOT_TASK_FILES.entrySet()) {
            if (Objects.equal(botTaskFile, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Gets bot task by file.
     */
    public static synchronized BotTask getBotTaskNotNull(IFile botTaskFile) {
        BotTask botTask = getBotTask(botTaskFile);
        if (botTask != null) {
            return botTask;
        }
        throw new RuntimeException("bot task not found by file '" + botTaskFile + "'");
    }

    /**
     * @return info file (without extension), not <code>null</code> for existing bot task
     */
    public static synchronized IFile getBotTaskFile(BotTask botTask) {
        if (!BOT_TASK_FILES.containsKey(botTask)) {
            throw new RuntimeException("No file exist for bot task " + botTask.getName());
        }
        return BOT_TASK_FILES.get(botTask);
    }
}