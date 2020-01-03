package com.tst.automation.opcua.project.pojo;

import lombok.Data;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;

import java.util.Date;

@Data
public class OpcUaDataValue {

    private DataValue dataValue;

    private String quality;                 // StatusCode.quality

    private String value;                   // DataValue.Variant.Value

    private Date serverDateTime;



    public OpcUaDataValue(DataValue dataValue) {
        this.dataValue = dataValue;
        this.quality = getQuality(dataValue.getStatusCode());
        this.value = dataValue.getValue().getValue().toString();

        NodeId typeNodeId = dataValue.getValue().getDataType().get();
        // System.out.println("typeNodeId: " + typeNodeId.toParseableString());
        // TODO
        // dateType = ????
        this.serverDateTime = dataValue.getServerTime().getJavaDate();
    }

    public String getQuality(StatusCode statusCode) {
        if (statusCode.isGood()) {
            return "good";
        } else if (statusCode.isBad()) {
            return "bad";
        } else if (statusCode.isUncertain()) {
            return "uncertain";
        } else {
            return "unknown";
        }
    }
}
