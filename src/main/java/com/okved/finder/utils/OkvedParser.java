package com.okved.finder.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class OkvedParser {

    public static class OkvedItem {
        private String code;
        private String name;

        public OkvedItem() {
        }

        public OkvedItem(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return code + ": " + name;
        }
    }


    /**
     * Рекурсивно обходит JsonNode и собирает объекты с полями code и name
     */
    private static void traverseJsonNode(JsonNode node, List<OkvedItem> items) {
        if (node == null) {
            return;
        }

        // Если текущий узел - объект и содержит поля code и name
        if (node.isObject() && node.has("code") && node.has("name")) {
            if (node.get("code").asText().chars().anyMatch(Character::isDigit)) {
                String code = node.get("code").asText().replaceAll("\\D", "");
                String name = node.get("name").asText();
                items.add(new OkvedItem(code, name));
            }
            ;
        }

        // Рекурсивно обрабатываем все поля объекта
        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                traverseJsonNode(entry.getValue(), items);
            });
        }

        // Рекурсивно обрабатываем элементы массива
        if (node.isArray()) {
            for (JsonNode arrayItem : node) {
                traverseJsonNode(arrayItem, items);
            }
        }
    }

    /**
     * Парсит JSON строку и извлекает все объекты с полями code и name
     *
     * @param jsonString JSON строка
     * @return список всех найденных объектов с code и name
     */
    public static List<OkvedItem> parseAllItemsWithCodeAndName(String jsonString) throws IOException {
        List<OkvedItem> items = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);

        traverseJsonNode(root, items);

        return items;
    }

}