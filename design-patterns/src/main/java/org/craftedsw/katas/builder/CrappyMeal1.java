package org.craftedsw.katas.builder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class CrappyMeal1 {

    public static class CrappyMealBuilder {
        private List<Burger> burgers = new ArrayList<Burger>();
        private Fries fries;
        private Drink drink;
        private Dessert dessert;

        public CrappyMealBuilder withBurger(Burger burger) {
            this.burgers.add(burger);
            return this;
        }

        public CrappyMealBuilder withFries() {
            this.fries = new Fries();
            return this;
        }

        public CrappyMealBuilder withDrink(Drink drink) {
            this.drink = drink;
            return this;
        }

        public CrappyMealBuilder withDessert(Dessert dessert) {
            this.dessert = new Dessert();
            return this;
        }

        public CrappyMeal1 build() {
            CrappyMeal1 meal = new CrappyMeal1();
            for (Burger burger : burgers) {
                meal.addBurger(burger);
            }
            meal.setDessert(this.dessert);
            meal.setDrink(this.drink);
            meal.setFries(this.fries);
            return meal;
        }

    }


    private List<Burger> burgers;
    private Fries fries;
    private Drink drink;
    private Dessert dessert;

    public CrappyMeal1() {
        this.burgers = new ArrayList<Burger>();
    }

    public CrappyMeal1(Burger burger) {
        this();
        addBurger(burger);
    }

    public CrappyMeal1(Burger burger, Fries fries) {
        this(burger);
        this.fries = fries;
    }
    //And so on...


    public void addBurger(Burger burger) {
        this.burgers.add(burger);
    }

    public List<Burger> getBurgers() {
        return burgers;
    }

    public Fries getFries() {
        return fries;
    }

    public void setFries(Fries fries) {
        this.fries = fries;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public Dessert getDessert() {
        return dessert;
    }

    public void setDessert(Dessert dessert) {
        this.dessert = dessert;
    }


    public static void main(String[] args) {
        CrappyMealBuilder builder = new CrappyMealBuilder();
        CrappyMeal1 meal1 = builder.withBurger(new Burger())
                .withFries()
                .withDessert(new Dessert())
                .withDrink(new Drink())
                .build();

        CrappyMeal1 meal2 = builder.withBurger(new Burger())
                .withDrink(new Drink())
                .build();
    }
}
