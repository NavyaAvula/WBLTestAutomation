package com.wbl.utils.rest;

import com.wbl.utils.web.PageDriver;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Shilpi on 8/31/2015.
 */
public class WJsonObject {

    private Logger _logger;
    private JSONArray jsonArray;
    private JSONObject jsonObj;
    //private JSONObject childJson;
    //private List<JSONObject> childJsonList = new ArrayList<JSONObject>();


    public WJsonObject(JSONObject obj)
    {
         this.jsonObj = obj;
        _logger = Logger.getLogger(PageDriver.class);
    }

    /*public void setJsonArray(String mJsonString)
    {
        try {
            this.jsonArray  = new JSONArray(mJsonString);
        } catch (JSONException e) {
            _logger.error(e);
        }
    }

    public void setJsonObject(String mJsonString)
    {
        try {
            this.jsonObj  = new JSONObject(mJsonString);
        } catch (JSONException e) {
            _logger.error(e);
        }
    }*/

    public JSONObject getJsonObject(String mKey)
    {
        Object json;
        WJsonObject childObj = null;
        try {
            json = jsonObj.get(mKey);
            if(json != null && (json instanceof JSONObject || json instanceof JSONArray))
            {
                this.jsonObj = (JSONObject)json;
            }
        } catch (JSONException e) {
            _logger.error(e);
        }

        return this.jsonObj;
    }

    public boolean isKeyAvailable(String mKey)
    {
        boolean available = false;
        Iterator<String> jsonKeys = jsonObj.keys();
        while (jsonKeys.hasNext())
        {
            String key = jsonKeys.next();
            if(key.equals(mKey))
            {
                available = true;
                break;
            }

        }
        return available;

    }

    public boolean isValueNotNull(String mKey)
    {
        try {
            boolean  isNull = jsonObj.get(mKey).toString() != null ? true:false;
            return isNull;
        } catch (JSONException e) {
            _logger.error(e);
        }
        return true;
    }

    public String getJsonValue(String mKey)
    {
        String value = null;
        try {
            value = jsonObj.get(mKey).toString();
        } catch (JSONException e) {
            _logger.error(e);
        }
        return value;
    }

    public int getJsonIntValue(String mKey)
    {
        int intValue = 0;
        String value = null;
        try {
            value = jsonObj.get(mKey).toString();
            if(!StringUtils.isNumeric(value))
            {
                _logger.info("Passed key doesn't contain a valid integer value");
                return 0;
            }
            else{
                intValue = Integer.parseInt(value);
            }
        } catch (JSONException e) {
            _logger.error(e);
        }
        return intValue;
    }

    public boolean getJsonBooleanVal(String mKey)
    {
        boolean boolValue = true;
        String value = null;
        try {
            value = jsonObj.get(mKey).toString();
            boolValue = Boolean.valueOf(value);
          /*  if(!Boolean.valueOf(value))
            {
                _logger.info("Passed key doesn't contain a valid boolean value");
                return false;
            }
            else{
                boolValue = Boolean.valueOf(value);
            }*/
        } catch (JSONException e) {
            _logger.error(e);
        }
        return boolValue;
    }

    public boolean isPropertyArray(String mKey)
    {
        boolean isArray = false;
        Object obj = null;
        try {
            obj = jsonObj.get(mKey);
            if(obj != null && obj instanceof JSONArray)
            {
                isArray = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return isArray;
    }

    public String getJsonArrayElement(String mKey,int index)
    {
        String value = null;
        Object obj = null;
        JSONArray array;
        try {
            obj = jsonObj.get(mKey);
            if(obj == null || !(obj instanceof JSONArray))
            {
                _logger.info("Passed value is not a valid json array");
                return null;
            }
            else
            {
                array = (JSONArray)obj;
                value = getElement(array,index);
                value = array.get(index).toString();
            }
        } catch (JSONException e) {
            _logger.error(e);
        }
        return value;
    }

    public String getElement(JSONArray array , int index)
    {
        try {
            return array.get(index).toString();
        } catch (JSONException e) {
            _logger.error(e);
        }

        return "";
    }

    /*

    public void setChildJson()
    {
        Iterator<String> keys = jsonObj.keys();
        try {
        while (keys.hasNext())
        {

                Object obj = jsonObj.get(keys.next());
               if(obj != null && obj instanceof JSONObject)
               {
                   this.childJsonList.add((JSONObject) obj);
               }
        }
        } catch (JSONException e) {
            _logger.error(e);
        }
    }

    public String getChildValue(String mKey)
    {
        try {
            return childJson.get(mKey).toString();
        } catch (JSONException e) {
            _logger.error(e);
        }
        return "";
    }*/

    public int getPropertyCount()
    {
       return jsonObj.length();
    }
    public int getArrayCount() {
        return jsonArray.length();
    }
}
