package ru.runa.gpd.extension.orgfunction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.eclipse.core.runtime.IProgressMonitor;

import ru.runa.gpd.util.XmlUtil;
import ru.runa.gpd.wfe.DataImporter;
import ru.runa.gpd.wfe.WFEServerConnector;
import ru.runa.service.af.RelationService;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.presentation.BatchPresentationConsts;
import ru.runa.wfe.presentation.BatchPresentationFactory;
import ru.runa.wfe.relation.Relation;

public class WFEServerRelationsImporter extends DataImporter {
    private final List<String> relations = new ArrayList<String>();
    private static WFEServerRelationsImporter instance;

    private WFEServerRelationsImporter() {
        super(WFEServerConnector.getInstance());
    }

    public static synchronized WFEServerRelationsImporter getInstance() {
        if (instance == null) {
            instance = new WFEServerRelationsImporter();
        }
        return instance;
    }

    @Override
    protected void clearInMemoryCache() {
        relations.clear();
    }

    @Override
    protected void saveCachedData() throws Exception {
        Document document = XmlUtil.createDocument("data");
        for (String name : relations) {
            Element element = document.getRootElement().addElement("relation");
            element.addAttribute("name", name);
        }
        XmlUtil.writeXml(document, new FileOutputStream(getCacheFile()));
    }

    @Override
    public List<String> loadCachedData() throws Exception {
        List<String> result = new ArrayList<String>();
        File cacheFile = getCacheFile();
        if (cacheFile.exists()) {
            Document document = XmlUtil.parseWithoutValidation(new FileInputStream(cacheFile));
            List<Element> nodeList = document.getRootElement().elements("relation");
            for (Element element : nodeList) {
                String name = element.attributeValue("name");
                result.add(name);
            }
        }
        return result;
    }

    @Override
    protected void loadRemoteData(IProgressMonitor monitor) throws Exception {
        RelationService executorService = WFEServerConnector.getInstance().getService("RelationServiceBean");
        BatchPresentation batchPresentation = BatchPresentationFactory.RELATIONS.createDefault();
        batchPresentation.setRangeSize(BatchPresentationConsts.MAX_UNPAGED_REQUEST_SIZE);
        List<Relation> loaded = executorService.getRelations(WFEServerConnector.getInstance().getSubject(), batchPresentation);
        for (Relation relation : loaded) {
            relations.add(relation.getName());
        }
        monitor.worked(100);
    }
}
