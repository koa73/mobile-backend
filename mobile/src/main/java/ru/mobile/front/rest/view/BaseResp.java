package ru.mobile.front.rest.view;

import com.fasterxml.jackson.annotation.*;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "cmdCode",
        "operId",
        "resultCode",
        "errMsg"
})
public class BaseResp {

    @JsonProperty("cmdCode")
    private Integer cmdCode;
    @JsonProperty("resultCode")
    private Integer resultCode;
    @JsonProperty("errMsg")
    private String errMsg;
    @JsonProperty("operId")
    private String operId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public BaseResp(int cmdCode){
        this.cmdCode = cmdCode;
        this.resultCode = 0;
    }

    public BaseResp(int cmdCode, String operId){
        this.cmdCode = cmdCode;
        this.operId = operId;
        this.resultCode = 0;
    }

    public BaseResp(int cmdCode, String operId, int resultCode){
        this.cmdCode = cmdCode;
        this.operId = operId;
        this.resultCode = resultCode;
    }


    @JsonProperty("cmdCode")
    public Integer getCmdCode() {
        return cmdCode;
    }

    @JsonProperty("cmdCode")
    public void setCmdCode(Integer cmdCode) {
        this.cmdCode = cmdCode;
    }

    @JsonProperty("resultCode")
    public Integer getResultCode() {
        return resultCode;
    }

    @JsonProperty("resultCode")
    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    @JsonProperty("errMsg")
    public String getErrMsg() {
        return errMsg;
    }

    @JsonProperty("errMsg")
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @JsonProperty("operId")
    public String getOperId() {
        return operId;
    }

    @JsonProperty("operId")
    public void setOperId(String operId) {
        this.operId = operId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}