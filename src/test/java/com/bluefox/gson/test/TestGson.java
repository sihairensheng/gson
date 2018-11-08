package com.bluefox.gson.test;


import com.bluefox.gson.entity.UserBean;
import com.bluefox.gson.entity.WorkInfo;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

public class TestGson {

    //Gson
    Gson gson = new Gson();

    //Json的解析类对象
    JsonParser parser = new JsonParser();

    //解析没有数据头的纯数组
    //@Test
    public void noHeaderArray() {
        String noHeaderArrayString = "["+
                "{\"name\":\"zhangsan\","+
                "\"age\":\"10\","+
                "\"phone\":\"11111\","+
                "\"email\":\"11111@11.com\"},"+
                "{\"name\":\"lisi\"," +
                "\"age\":\"20\","+
                "\"phone\":\"22222\","+
                "\"email\":\"22222@22.com\"}"+
                "]";
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(noHeaderArrayString).getAsJsonArray();
        //加强for循环遍历JsonArray
        for (JsonElement user : jsonArray) {
            UserBean userBean = gson.fromJson(user, UserBean.class);
            System.err.println("User{\"userName\":\"" + userBean.getName() + "\",\"userAge\":\"" + userBean.getAge() + "\",\"userPhone\":\"" + userBean.getPhone() + "\",\"userEmail\":\"" + userBean.getEmail() + "\"}");
        }
    }

    //解析有数据头的纯数组
    //@Test
    public void headerArray() {
        String headerArrayString = "{" +
                "\"muser\":[" +
                "{" +
                "\"name\":\"zhangsan\"," +
                "\"age\":\"10\"," +
                "\"phone\":\"11111\"," +
                "\"email\":\"11111@11.com\"" +
                "}," +
                "{" +
                "\"name\":\"lisi\"," +
                "\"age\":\"20\"," +
                "\"phone\":\"22222\"," +
                "\"email\":\"22222@22.com\"" +
                "}" +
                "]" +
                "}";
        //将JSON的String 转成一个JsonObject对象
        JsonObject jsonObject = parser.parse(headerArrayString).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("muser");
        for (JsonElement user : jsonArray) {
            UserBean userBean = gson.fromJson(user, new TypeToken<UserBean>() {
            }.getType());
            System.err.println("User{\"userName\":\"" + userBean.getName() + "\",\"userAge\":\"" + userBean.getAge() + "\",\"userPhone\":\"" + userBean.getPhone() + "\",\"userEmail\":\"" + userBean.getEmail() + "\"}");
        }
    }

    //解析有数据头的复杂数据
    //@Test
    public void headerComplexData() {
        String headerComplexData = "{" +
                "\"code\":200," +
                "\"msg\":\"OK\"," +
                "\"muser\":[" +
                "{" +
                "\"name\":\"zhangsan\"," +
                "\"age\":\"10\"," +
                "\"phone\":\"11111\"," +
                "\"email\":\"11111@11.com\"" +
                "}," +
                "{" +
                "\"name\":\"lisi\"," +
                "\"age\":\"20\"," +
                "\"phone\":\"22222\"," +
                "\"email\":\"22222@22.com\"" +
                "}" +
                "]" +
                "}";
        JsonObject jsonObject = parser.parse(headerComplexData).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("muser");
        for (JsonElement user : jsonArray) {
            UserBean userBean = gson.fromJson(user, new TypeToken<UserBean>() {
            }.getType());
            System.err.println("User{\"userName\":\"" + userBean.getName() + "\",\"userAge\":\"" + userBean.getAge() + "\",\"userPhone\":\"" + userBean.getPhone() + "\",\"userEmail\":\"" + userBean.getEmail() + "\"}");
        }

    }

    //解析并截取有数据头的复杂数据
    //@Test
    public void interceptHeaderComplexData() {
        String interceptHeaderComplexData = "{" +
                "\"code\":200," +
                "\"msg\":\"OK\"," +
                "\"muser\":[" +
                "{" +
                "\"name\":\"zhangsan\"," +
                "\"age\":\"10\"," +
                "\"phone\":\"11111\"," +
                "\"email\":\"11111@11.com\"" +
                "}," +
                "{" +
                "\"name\":\"lisi\"," +
                "\"age\":\"20\"," +
                "\"phone\":\"22222\"," +
                "\"email\":\"22222@22.com\"" +
                "}" +
                "]" +
                "}";
        JsonObject jsonObject = parser.parse(interceptHeaderComplexData).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("muser");
        for (JsonElement user : jsonArray) {
            UserBean userBean = gson.fromJson(user, new TypeToken<UserBean>() {
            }.getType());
            if (userBean.getAge() > 10) {
                System.err.println("User{\"userName\":\"" + userBean.getName() + "\",\"userAge\":\"" + userBean.getAge() + "\",\"userPhone\":\"" + userBean.getPhone() + "\",\"userEmail\":\"" + userBean.getEmail() + "\"}");
            }
        }
    }

    //解析超复杂数据
    //@Test
    public void complexData() {
        String complexDataString = "{" +
                "\"group\":{" +
                "\"user\":{" +
                "\"name\":\"张三\"," +
                "\"age\":\"10\"," +
                "\"phone\":\"11111\"," +
                "\"email\":\"11111@11.com\"" +
                "}," +
                "\"info\":{" +
                "\"address\":\"北京\"," +
                "\"work\":\"Android Dev\"," +
                "\"pay\":\"10K\"," +
                "\"motto\":\"先定一个小目标，比如我先赚一个亿\"" +
                "}" +
                "}" +
                "}";
        JsonObject jsonObject = parser.parse(complexDataString).getAsJsonObject();
        JsonObject groupJsonObject = jsonObject.getAsJsonObject("group");
        JsonObject userJsonObject = groupJsonObject.getAsJsonObject("user");
        UserBean userBean = gson.fromJson(userJsonObject, new TypeToken<UserBean>() {
        }.getType());
        System.err.println("User{\"userName\":\"" + userBean.getName() + "\",\"userAge\":\"" + userBean.getAge() + "\",\"userPhone\":\"" + userBean.getPhone() + "\",\"userEmail\":\"" + userBean.getEmail() + "\"}");
        JsonObject infoJsonObject = groupJsonObject.getAsJsonObject("info");
        WorkInfo workInfo = gson.fromJson(infoJsonObject, new TypeToken<WorkInfo>() {
        }.getType());
        System.err.println("WorkInfo{\"address\":\"" + workInfo.getAddress() + "\",\"work\":\"" + workInfo.getWork() + "\",\"pay\":\"" + workInfo.getPay() + "\",\"motto\":\"" + workInfo.getMotto() + "\"}");
    }

    //使用JsonReader解析复杂数据
    @Test
    public void jsonReaderComplexData() throws IOException {
        String complexDataString = "["
                + "{\"id\":912345678901,"
                + "\"text\":\"How do I read JSON on Android?\","
                + "\"geo\":null,"
                + "\"user\":{\"name\":\"android_newb\",\"followers_count\":41}},"
                + "{\"id\":912345678902,"
                + "\"text\":\"@android_newb just use android.util.JsonReader!\","
                + "\"geo\":[50.454722,-104.606667],"
                + "\"user\":{\"name\":\"jesse\",\"followers_count\":2}}"
                + "]";
        JsonReader jsonReader = new JsonReader(new StringReader(complexDataString));
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            readMessage(jsonReader);
        }
        jsonReader.endArray();
    }

    private void readMessage(JsonReader jsonReader) throws IOException{
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String tagName = jsonReader.nextName();
            if ("id".equals(tagName)) {
                System.err.println("\"id\":" + jsonReader.nextLong());
            } else if ("text".equals(tagName)) {
                System.err.println("\"text\":\"" + jsonReader.nextString()+"\"");
            } else if ("geo".equals(tagName) && jsonReader.peek() != JsonToken.NULL){
                readGeo(jsonReader);
            } else if ("user".equals(tagName)) {
                readUser(jsonReader);
            }else{
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
    }

    private void readGeo(JsonReader jsonReader) throws IOException{
        jsonReader.beginArray();
        while (jsonReader.hasNext()){
            System.err.println(jsonReader.nextDouble());
        }
        jsonReader.endArray();
    }

    private void readUser(JsonReader jsonReader) throws IOException{
        jsonReader.beginObject();
        while(jsonReader.hasNext()){
            String tagName = jsonReader.nextName();
            if("name".equals(tagName)){
                System.err.println("\"name\":\""+jsonReader.nextString()+"\"");
            }
            else if("followers_count".equals(tagName)){
                System.err.println("\"followers_count\":"+jsonReader.nextInt());
            }
        }
        jsonReader.endObject();
    }

}
