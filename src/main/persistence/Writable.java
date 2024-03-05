package persistence;

import org.json.JSONObject;

//Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//          from CPSC 210 - JsonSerializationDemo

public interface Writable {
    //EFFECTS: return this as JSON object
    JSONObject toJson();
}
