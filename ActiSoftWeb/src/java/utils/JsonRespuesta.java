/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


import java.util.List;

/**
 *
 * @author Juan Manuel
 */
public class JsonRespuesta {

    String Result;
    List Records;
    List Options;
    int TotalRecordCount;
    Object Record;
    String Message;
    

    public List getRecords() {
        return Records;
    }

    public void setRecords(List Records) {
        this.Records = Records;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public int getTotalRecordCount() {
        return TotalRecordCount;
    }

    public void setTotalRecordCount(int TotalRecordCount) {
        this.TotalRecordCount = TotalRecordCount;
    }

    public Object getRecord() {
        return Record;
    }

    public void setRecord(Object Record) {
        this.Record = Record;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    void setOptions(List list) {
        this.Options = list;
    }
    
    
}
