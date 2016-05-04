package br.com.ifba.swseditormobile.model;

/**
 * Created by Robson on 03/05/2016.
 */
public class OutputSemantico {
    private String modelReference;
    private String lowering;
    private String lifting;


    public String getModelReference() {
        return modelReference;
    }

    public void setModelReference(String modelReference) {
        this.modelReference = modelReference;
    }

    public String getLowering() {
        return lowering;
    }

    public void setLowering(String lowering) {
        this.lowering = lowering;
    }

    public String getLifting() {
        return lifting;
    }

    public void setLifting(String lifting) {
        this.lifting = lifting;
    }
}



