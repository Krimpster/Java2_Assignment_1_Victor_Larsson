package org.example;

import org.example.objects.Building;
import org.example.objects.Worker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static java.lang.System.in;
import static org.junit.jupiter.api.Assertions.*;

public class VillageTest {

    InputStream systemIn = in;

    @AfterEach
    void restoreSystemIO(){
        System.setIn(systemIn);
    }

    @Test
    void FeedWorkers_Should_FeedWorkersAnd_Day_Should_Advance(){
        Village village = new Village();
        int expectedFood = 4;
        int expectedDaysGone = 1;

        village.AddWorker("Test1", "builder");
        village.AddWorker("Test2", "builder");
        village.AddWorker("Test3", "builder");
        village.AddWorker("Test4", "builder");
        village.AddWorker("Test5", "builder");
        village.AddWorker("Test6", "builder");

        village.Day();

        for(Worker worker : village.getWorkers()){
            assertTrue(worker.isHungry());
        }

        assertEquals(expectedFood, village.getFood());
        System.out.println("Expected food after feeding: " + expectedFood + " Actual food after feeding: " + village.getFood());
        assertEquals(expectedDaysGone, village.getDaysGone());
        System.out.println("Expected days passed after feeding: " + expectedDaysGone + " Actual days passed after feeding: " + village.getDaysGone());
    }

    @Test
    void Workers_ShouldNot_GetOverCapped(){
        Village village = new Village();
        int unexpectedWorkerCount = 7;

        village.AddWorker("Test1", "builder");
        village.AddWorker("Test2", "builder");
        village.AddWorker("Test3", "builder");
        village.AddWorker("Test4", "builder");
        village.AddWorker("Test5", "builder");
        village.AddWorker("Test6", "builder");

        if(!village.isFull()){
            village.AddWorker("Test7", "builder");
            assertTrue(village.isFull());
        }
        else {
            System.out.println("The village is full.");
        }

        assertNotEquals(unexpectedWorkerCount, village.getWorkers().size());
        System.out.println("Expected worker count: " + unexpectedWorkerCount + " Actual worker count: " + village.getWorkers().size());
    }

    @Test
    void Resources_ShouldBe_Added_EachDay(){
        Village village = new Village();
        int expectedFood = 12;
        int expectedMetal = 1;
        int expectedWood = 1;

        village.AddWorker("Test1", "farmer");
        village.AddWorker("Test2", "miner");
        village.AddWorker("Test3", "lumberjack");

        village.Day();

        assertEquals(expectedFood, village.getFood());
        assertEquals(expectedMetal, village.getMetal());
        assertEquals(expectedWood, village.getWood());
    }

    @Test
    void Resources_ShouldBe_AddedBasedOnBuildingsToo(){
        Village village = new Village();
        int expectedMaxWorkers = village.getMaxWorkers() + 2;
        int expectedFoodPerDay = village.getFoodPerDay() + 5;
        int expectedMetalPerDay = village.getMetalPerDay() + 1;
        int expectedWoodPerDay = village.getWoodPerDay() + 1;

        village.setFood(99);
        village.setWood(18);
        village.setMetal(8);

        village.AddWorker("Test1", "builder");
        village.AddWorker("Farmer", "farmer");
        village.AddWorker("Miner", "miner");
        village.AddWorker("Lumberjack", "lumberjack");

        village.AddProject("House");
        village.AddProject("Farm");
        village.AddProject("Quarry");
        village.AddProject("Woodmill");

        for(int i = 0; i <= 20; i++){
            village.Day();
        }

        assertEquals(expectedMaxWorkers, village.getMaxWorkers());
        assertEquals(expectedFoodPerDay, village.getFoodPerDay());
        assertEquals(expectedMetalPerDay, village.getMetalPerDay());
        assertEquals(expectedWoodPerDay, village.getWoodPerDay());
    }

    @Test
    void AddProject_Should_AddProject_WhenSufficientMaterials(){
        Village village = new Village();
        village.setMetal(50);
        village.setWood(50);
        int expectedMetal = 49;
        int expectedWood = 45;
        int expectedProjects = 4;

        village.AddWorker("Test1", "farmer");
        village.AddWorker("Test2", "farmer");
        village.AddWorker("Test3", "builder");

        village.AddProject("Woodmill");
        assertFalse(village.getProjects().isEmpty());
        assertEquals(expectedWood, village.getWood());
        assertEquals(expectedMetal, village.getMetal());

        for(int i = 0; i < 5; i++){
            village.Day();
        }
        assertEquals(village.getBuildings().size(), expectedProjects);
    }

    @Test
    void AddProject_Should_AddProject_WhenSufficientMaterials2(){
        Village village = new Village();
        village.setMetal(50);
        village.setWood(50);
        int expectedMetal = 50;
        int expectedWood = 45;
        int expectedProjects = 4;

        village.AddWorker("Test1", "farmer");
        village.AddWorker("Test2", "farmer");
        village.AddWorker("Test3", "builder");

        village.AddProject("House");
        assertFalse(village.getProjects().isEmpty());
        assertEquals(expectedWood, village.getWood());
        assertEquals(expectedMetal, village.getMetal());

        for(int i = 0; i < 3; i++){
            village.Day();
        }
        assertEquals(village.getBuildings().size(), expectedProjects);
    }

    @Test
    void AddProject_ShouldNot_AddProject_WhenInsufficientMaterials1() {
        Village village = new Village();

        village.AddWorker("Test1", "farmer");
        village.AddWorker("Test2", "farmer");
        village.AddWorker("Test3", "builder");

        village.AddProject("Woodmill");
        assertTrue(village.getProjects().isEmpty());
    }

    @Test
    void AddProject_ShouldNot_AddProject_WhenInsufficientMaterials2() {
        Village village = new Village();

        village.setMetal(2);
        village.setWood(3);

        village.AddWorker("Test1", "farmer");
        village.AddWorker("Test2", "farmer");
        village.AddWorker("Test3", "builder");

        village.AddProject("Woodmill");
        assertTrue(village.getProjects().isEmpty());
    }

    @Test
    void AddProject_ShouldNot_AddProject_WhenNameIsntABuilding() {
        Village village = new Village();

        village.AddWorker("Test1", "farmer");
        village.AddWorker("Test2", "farmer");
        village.AddWorker("Test3", "builder");

        village.AddProject("gook");
        assertTrue(village.getProjects().isEmpty());
    }

    @Test
    void AddProject_ShouldNot_AddProject_WhenNameIsntABuilding2() {
        Village village = new Village();

        village.AddWorker("Test1", "farmer");
        village.AddWorker("Test2", "farmer");
        village.AddWorker("Test3", "builder");

        village.AddProject("Harvard");
        assertTrue(village.getProjects().isEmpty());
    }

    @Test
    void GameOver_IfNoWorkers(){
        Village village = new Village();
        village.AddWorker("Test1", "builder");
        village.AddWorker("Test2", "builder");
        village.AddWorker("Test3", "builder");
        village.AddWorker("Test4", "builder");
        village.AddWorker("Test5", "builder");
        village.AddWorker("Test6", "builder");

        for(int i = 0; i < 7; i++) {
            village.Day();
        }

        assertTrue(village.isGameOver());
        System.out.println("Is the game over? " + village.isGameOver());
    }

    @Test
    void GameOver_ShouldNotHappenIf_NoWorkersAreAdded(){
        Village village = new Village();

        village.Day();

        assertFalse(village.isGameOver());
    }

    @Test
    void GameOver_ShouldHappenIf_CastleIsBuilt(){
        Village village = new Village();

        village.setWood(50);
        village.setMetal(50);
        village.setFood(100);

        village.AddWorker("Test1", "builder");
        village.AddWorker("Test2", "farmer");

        village.AddProject("Castle");

        for(int i = 0; i < 50; i++){
            village.Day();
        }

        assertTrue(village.isGameOver());
    }

    @Test
    void PlayingTheWholeGame_ShouldMake_GameOver(){
        Village village = new Village();

        village.AddWorker("Test1", "farmer");
        village.AddWorker("Test2", "farmer");
        village.AddWorker("Test3", "builder");
        village.AddWorker("Test4", "lumberjack");
        village.AddWorker("Test5", "lumberjack");
        village.AddWorker("Test6", "lumberjack");

        for(int i = 0; i <= 68; i++){
            if (i == 2){
                village.AddProject("House");
                village.Day();
            }
            else if (i == 4) {
                village.AddProject("House");
                village.Day();
            }
            else if (i == 5) {
                village.AddWorker("Test7", "farmer");
                village.AddWorker("Test8", "miner");
                village.AddWorker("Test9", "miner");
                village.Day();
            }
            else if (i == 7){
                village.AddWorker("Test10", "lumberjack");
                village.AddWorker("Test11", "miner");
                village.AddWorker("Test12", "miner");
                village.Day();
            }
            else if (i == 19){
                village.AddProject("Castle");
                village.Day();
            }
            else {
                village.Day();
            }
        }
        assertTrue(village.isGameOver());
    }

    @Test
    void PrintInfo_Should_PrintCorrectInfo(){
        Village village = new Village();
        int expectedFood = 21;
        int expectedWood = 2;
        int expectedMetal = 80;
        int expectedFoodPD = 5;
        int expectedWoodPD = 1;
        int expectedMetalPD = 1;
        int expectedBuildingsSize = 5;
        int expectedProjectsSize = 2;
        int expectedWorkersSize = 6;

        village.setFood(17);
        village.setWood(10);
        village.setMetal(80);

        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(new Building("House"));
        buildings.add(new Building("House"));
        buildings.add(new Building("House"));
        buildings.add(new Building("Quarry"));
        buildings.add(new Building("Woodmill"));
        village.setBuildings(buildings);
        village.AddProject("House");
        village.AddProject("Woodmill");
        village.AddWorker("Test1", "farmer");
        village.AddWorker("Test2", "farmer");
        village.AddWorker("Test3", "miner");
        village.AddWorker("Test4", "lumberjack");
        village.AddWorker("Test5", "lumberjack");
        village.AddWorker("Test6", "builder");

        village.Day();

        village.PrintInfo();

        assertEquals(expectedFood, village.getFood());
        assertEquals(expectedMetal, village.getMetal());
        assertEquals(expectedWood, village.getWood());
        assertEquals(expectedFoodPD, village.getFoodPerDay());
        assertEquals(expectedMetalPD, village.getMetalPerDay());
        assertEquals(expectedWoodPD, village.getWoodPerDay());
        assertEquals(expectedBuildingsSize, village.getBuildings().size());
        assertEquals(expectedProjectsSize, village.getProjects().size());
        assertEquals(expectedWorkersSize, village.getWorkers().size());
    }

}
