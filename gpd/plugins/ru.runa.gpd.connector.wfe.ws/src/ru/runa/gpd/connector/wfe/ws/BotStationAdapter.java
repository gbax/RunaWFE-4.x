package ru.runa.gpd.connector.wfe.ws;

import java.util.List;

import ru.runa.wfe.bot.BotStation;

import com.google.common.collect.Lists;

public class BotStationAdapter {
    public static BotStation toDTO(ru.runa.wfe.webservice.BotStation botStation) {
        BotStation result = new BotStation();
        result.setAddress(botStation.getAddress());
        result.setName(botStation.getName());
        return result;
    }

    public static List<BotStation> toDTOs(List<ru.runa.wfe.webservice.BotStation> botStations) {
        List<BotStation> result = Lists.newArrayListWithExpectedSize(botStations.size());
        for (ru.runa.wfe.webservice.BotStation bot : botStations) {
            result.add(toDTO(bot));
        }
        return result;
    }

    public static ru.runa.wfe.webservice.BotStation toJAXB(BotStation botStation) {
        ru.runa.wfe.webservice.BotStation result = new ru.runa.wfe.webservice.BotStation();
        result.setAddress(botStation.getAddress());
        result.setName(botStation.getName());
        return result;
    }
}
