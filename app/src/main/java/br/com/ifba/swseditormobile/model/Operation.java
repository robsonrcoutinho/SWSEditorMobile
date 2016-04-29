package br.com.ifba.swseditormobile.model;

/**
 * Created by Robson on 29/04/2016.
 */
public class Operation {
    private String nomeOperation;
    private String tipoOperation;
    private String paramOperation;
    private String inputOperation;
    private String outputOperation;

    public String getNomeOperation() {
        return nomeOperation;
    }

    public void setNomeOperation(String nomeOperation) {
        this.nomeOperation = nomeOperation;
    }

    public String getTipoOperation() {
        return tipoOperation;
    }

    public void setTipoOperation(String tipoOperation) {
        this.tipoOperation = tipoOperation;
    }

    public String getOutputOperation() {
        return outputOperation;
    }

    public void setOutputOperation(String outputOperation) {
        this.outputOperation = outputOperation;
    }

    public String getParamOperation() {
        return paramOperation;
    }

    public void setParamOperation(String paramOperation) {
        this.paramOperation = paramOperation;
    }

    public String getInputOperation() {
        return inputOperation;
    }

    public void setInputOperation(String inputOperation) {
        this.inputOperation = inputOperation;
    }
}
