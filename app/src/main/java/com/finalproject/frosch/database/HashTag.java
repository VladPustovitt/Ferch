package com.finalproject.frosch.database;

public class HashTag{
    static class Type{
        final String name;
        public Type(String name){this.name = name;}
        public String getName() {
            return name;
        }
    }

    public static class Consumption extends Type{
        private Consumption(String name) {
            super(name);
        }

        public final static Consumption HOME = new Consumption("Жилье");
        public final static Consumption BOOK = new Consumption("Книга");
        public final static Consumption CLOTHE = new Consumption("Одежда");
        public final static Consumption PROGRAM = new Consumption("Программа");
        public final static Consumption DELIVERY = new Consumption("Доставка");
        public final static Consumption GADGET = new Consumption("Гаджет");
        public final static Consumption FOOD = new Consumption("Еда");
        public final static Consumption GROCERY = new Consumption("Магазин");
        public final static Consumption FUN = new Consumption("Веселье");
        public final static Consumption SPORT = new Consumption("Спорт");
        public final static Consumption SUBSCRIBE = new Consumption("Подписка");
        public final static Consumption TRANSPORT = new Consumption("Транспорт");
        public final static Consumption COMMUNAL = new Consumption("ЖКХ");
    }

    public static class Income extends Type{
        public Income(String name) {
            super(name);
        }

        public final static Income DEPOSIT = new Income("Вклад");
        public final static Income SAVING = new Income("Сбережения");
        public final static Income WIN = new Income("Победа в конкурсе");
        public final static Income SALARY = new Income("Зарплата");
        public final static Income TRANSFER = new Income("Перевод");

    }
}
