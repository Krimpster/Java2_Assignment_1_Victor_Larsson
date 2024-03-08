package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static java.lang.System.in;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class VillageInputTest {
    InputStream systemIn = in;
    Village village;
    DatabaseConnection db;

    @BeforeEach
    void setUp(){
        village = new Village();
        db = mock(DatabaseConnection.class);
    }

    @AfterEach
    void restoreSystemIO(){
        System.setIn(systemIn);
    }

    @Test
    void SaveVillage_ShouldSucceed(){
        String saveName = "Test Save";
        String input = saveName + "\n y \n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ArrayList<String> list = new ArrayList<>();
        list.add("These");
        list.add("are");
        list.add("placeholders");
        list.add("to");
        list.add("make");
        list.add("sure");
        list.add("it");
        list.add("works");

        when(db.GetTownNames()).thenReturn(list);
        when(db.SaveVillage(any(Village.class),eq(saveName))).thenReturn(true);

        VillageInput villageInput = new VillageInput(village, db);
        villageInput.Save();

        verify(db).SaveVillage(any(Village.class),eq(saveName));
    }

    @Test
    void SaveVillage_ShouldSucceed2(){
        String saveName = "These";
        String input = saveName + "\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ArrayList<String> list = new ArrayList<>();
        list.add("These");
        list.add("are");
        list.add("placeholders");
        list.add("to");
        list.add("make");
        list.add("sure");
        list.add("it");
        list.add("works");

        when(db.GetTownNames()).thenReturn(list);
        when(db.SaveVillage(any(Village.class),eq(saveName))).thenReturn(true);

        VillageInput villageInput = new VillageInput(village, db);
        villageInput.Save();

        verify(db).SaveVillage(any(Village.class),eq(saveName));
    }

    @Test
    void SaveVillage_ShouldSucceed3(){
        String saveName = "it";
        String input = saveName + "\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ArrayList<String> list = new ArrayList<>();
        list.add("These");
        list.add("are");
        list.add("placeholders");
        list.add("to");
        list.add("make");
        list.add("sure");
        list.add("it");
        list.add("works");

        when(db.GetTownNames()).thenReturn(list);
        when(db.SaveVillage(any(Village.class),eq(saveName))).thenReturn(true);

        VillageInput villageInput = new VillageInput(village, db);
        villageInput.Save();

        verify(db).SaveVillage(any(Village.class),eq(saveName));
    }

    @Test
    void SaveVillage_ShouldNotSucceed(){
        String saveName = "These";
        String input = saveName + "\nn\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ArrayList<String> list = new ArrayList<>();
        list.add("These");
        list.add("are");
        list.add("placeholders");
        list.add("to");
        list.add("make");
        list.add("sure");
        list.add("it");
        list.add("works");

        when(db.GetTownNames()).thenReturn(list);

        VillageInput villageInput = new VillageInput(village, db);
        villageInput.Save();

        verify(db, never()).SaveVillage(any(Village.class),eq(saveName));
    }

    @Test
    void LoadVillage_ShouldSucceed(){
        String loadName = "These";
        System.setIn(new ByteArrayInputStream(loadName.getBytes()));

        ArrayList<String> list = new ArrayList<>();
        list.add("These");
        list.add("are");
        list.add("placeholders");
        list.add("to");
        list.add("make");
        list.add("sure");
        list.add("it");
        list.add("works");

        when(db.GetTownNames()).thenReturn(list);
        when(db.LoadVillage(loadName)).thenReturn(village);

        VillageInput villageInput = new VillageInput(village, db);
        villageInput.Load();

        verify(db).LoadVillage(loadName);
    }

    @Test
    void LoadVillage_ShouldSucceed2(){
        String loadName = "placeholders";
        System.setIn(new ByteArrayInputStream(loadName.getBytes()));

        ArrayList<String> list = new ArrayList<>();
        list.add("These");
        list.add("are");
        list.add("placeholders");
        list.add("to");
        list.add("make");
        list.add("sure");
        list.add("it");
        list.add("works");

        when(db.GetTownNames()).thenReturn(list);
        when(db.LoadVillage(loadName)).thenReturn(village);

        VillageInput villageInput = new VillageInput(village, db);
        villageInput.Load();

        verify(db).LoadVillage(loadName);
    }

    @Test
    void LoadVillage_ShouldSucceed3(){
        String loadName = "it";
        System.setIn(new ByteArrayInputStream(loadName.getBytes()));

        ArrayList<String> list = new ArrayList<>();
        list.add("These");
        list.add("are");
        list.add("placeholders");
        list.add("to");
        list.add("make");
        list.add("sure");
        list.add("it");
        list.add("works");

        when(db.GetTownNames()).thenReturn(list);
        when(db.LoadVillage(loadName)).thenReturn(village);

        VillageInput villageInput = new VillageInput(village, db);
        villageInput.Load();

        verify(db).LoadVillage(loadName);
    }

    @Test
    void LoadVillage_ShouldNotSucceed(){
        String loadName = "Test Save";
        System.setIn(new ByteArrayInputStream(loadName.getBytes()));

        ArrayList<String> list = new ArrayList<>();
        list.add("These");
        list.add("are");
        list.add("placeholders");
        list.add("to");
        list.add("make");
        list.add("sure");
        list.add("it");
        list.add("works");

        when(db.GetTownNames()).thenReturn(list);
        when(db.LoadVillage(loadName)).thenReturn(village);

        VillageInput villageInput = new VillageInput(village, db);
        villageInput.Load();

        verify(db, never()).LoadVillage(loadName);
    }

}
