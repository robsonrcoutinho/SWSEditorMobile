package br.com.ifba.swseditormobile.util;

import android.util.Log;
import br.com.ifba.swseditormobile.model.InputSemantico;
import br.com.ifba.swseditormobile.model.OutputSemantico;
import br.com.ifba.swseditormobile.model.ServiceSematico;


/**
 * Created by #### on 19/01/2016.
 */
public class HTMLParser {
    private static final String TAG = HTMLParser.class.getSimpleName();
    private static String service;
    private static String address;
    private static String operation;
    private ServiceSematico serviceSematico;
    private InputSemantico inputSemantico;
    private OutputSemantico outputSemantico;
    private int opSemantic;
    private static String semanticOntologia;
    private static String semanticInput;
    private static String semanticoutput;

    private static final String asp = "\"";

    public HTMLParser(ServiceSematico service, InputSemantico inputSemantico, OutputSemantico outputSemantico ) {
        if (service != null) {
            this.serviceSematico = service;
            if (inputSemantico != null) {
                this.inputSemantico = inputSemantico;
                semanticService();
                semanticInput();
            }
            if (outputSemantico != null) {
                this.outputSemantico = outputSemantico;
                semanticService();
                semanticOutput();
            }
        }
    }

    public HTMLParser() {
    }


    private static String html = "<html>"
            + "<head>"
            + "<title>SWS Editor Mobile Editor</title>"
            + "</head>"
            + "<body style=\"background-color:lightgrey;\">"
            + "<h1>SWS Editor Mobile Editor</h1>";

    private static String meioHtml = "<h2>Exemplo de uso:</h2>"
            + "<p>O Address:</p> ";


    private static final String htmlFecha = "</body>"
            + "</html>";


    public static String montaHTML() {
        html = html.concat(service);
        if(semanticOntologia != null) {
            html = html.concat(semanticOntologia);
        }
        html = html.concat(meioHtml);
        html = html.concat(address);
        html = html.concat(operation);

        if(semanticInput != null) {
            html = html.concat(semanticInput);
        }
        if(semanticoutput != null) {
            html = html.concat(semanticoutput);
        }
        html = html.concat(htmlFecha);

        Log.d("HTML:", html);
        return html;
    }

    public void recebeService(String tag) {
        this.service = tag;


    }

    public void recebeAddress(String tag) {
        this.address = tag;

    }

    public void recebeOperation(String tag) {
        this.operation = tag;

    }

    public void semanticService() {
        if (serviceSematico.getModelReference() != null && !serviceSematico.getModelReference().isEmpty()) {
            String modelReference = "<strong> ModelReference:</strong>" +
                    " <a rel=" + asp + "model" + asp + " href=" + asp + serviceSematico.getModelReference().toString() + asp + ">" +" </br>"+
                    serviceSematico.getModelReference().toString() + "</a> </br>";
            this.semanticOntologia = modelReference;

            Log.d(TAG, "Service Semantic" + serviceSematico.getModelReference().toString());

            if (serviceSematico.getLowering() != null && !serviceSematico.getLowering().isEmpty()) {
                String lowering = "<strong> LoweringSchemaMapping:</strong>" +
                        "<a rel=" + asp + "lowering" + asp + " href=" + asp + serviceSematico.getLowering().toString() + asp + ">" +" </br>"+
                         serviceSematico.getLowering().toString()+"</a>  </br>";
                semanticOntologia += lowering;

                Log.d(TAG, "Service Semantic" + serviceSematico.getLowering().toString());

            }
            if(serviceSematico.getLifting()!= null && !serviceSematico.getLifting().isEmpty() ){
                String lifting =  "<strong> LiftingSchemaMapping:</strong>"+
                        "<a rel="+asp+"lifting"+asp+" href="+asp+ serviceSematico.getLifting().toString()+asp+">" +" </br>"+
                         serviceSematico.getLifting().toString()+"</a>  </br>";
                semanticOntologia+=lifting;
                Log.d(TAG, "Service Semantic" + serviceSematico.getLifting().toString());
            }

        }

    }

    private void semanticInput() {
        if (inputSemantico.getModelReference() != null && !inputSemantico.getModelReference().isEmpty()) {
            String modelReference = "<br><br>" +
                    "<strong> Ontologias para Parametro de Entrada:</strong> <br>"+
                    "<strong> ModelReference:</strong>" +
                    " <a rel=" + asp + "model" + asp + " href=" + asp + inputSemantico.getModelReference().toString() + asp + ">" +
                    inputSemantico.getModelReference().toString() + "</a> </br>";
            this.semanticInput = modelReference;

            Log.d(TAG, "inputSemantico Semantic" + inputSemantico.getModelReference().toString());

            if (inputSemantico.getLowering() != null && !inputSemantico.getLowering().isEmpty()) {
                String lowering = "<strong> LoweringSchemaMapping:</strong>" +
                        "<a rel=" + asp + "lowering" + asp + " href=" + asp + inputSemantico.getLowering().toString() + asp + ">" +
                        inputSemantico.getLowering().toString()+"</a>  </br>";
                semanticInput += lowering;

                Log.d(TAG, "inputSemantico Semantic" + inputSemantico.getLowering().toString());

            }
            if(inputSemantico.getLifting()!= null && !inputSemantico.getLifting().isEmpty() ){
                String lifting =  "<strong> LiftingSchemaMapping:</strong>"+
                        "<a rel="+asp+"lifting"+asp+" href="+asp+ inputSemantico.getLifting().toString()+asp+">" +
                        inputSemantico.getLifting().toString()+"</a>  </br>";
                semanticInput+=lifting;
                Log.d(TAG, "inputSemantico Semantic" + inputSemantico.getLifting().toString());
            }

        }

    }

    private void semanticOutput() {
        if (outputSemantico.getModelReference() != null && !outputSemantico.getModelReference().isEmpty()) {
            String modelReference = "<br><br>" +
                    "<strong> Ontologias para Parametro de saida:</strong> <br>"+
                    "<strong> ModelReference:</strong>" +
                    " <a rel=" + asp + "model" + asp + " href=" + asp + outputSemantico.getModelReference().toString() + asp + ">" +
                    outputSemantico.getModelReference().toString() + "</a> </br>";
            this.semanticoutput = modelReference;

            Log.d(TAG, "outputSemantico Semantic" + outputSemantico.getModelReference().toString());

            if (outputSemantico.getLowering() != null && !outputSemantico.getLowering().isEmpty()) {
                String lowering = "<strong> LoweringSchemaMapping:</strong>" +
                        "<a rel=" + asp + "lowering" + asp + " href=" + asp + outputSemantico.getLowering().toString() + asp + ">" +
                        outputSemantico.getLowering().toString()+"</a>  </br>";
                semanticoutput += lowering;

                Log.d(TAG, "outputSemantico Semantic" + outputSemantico.getLowering().toString());

            }
            if(outputSemantico.getLifting()!= null && !outputSemantico.getLifting().isEmpty() ){
                String lifting =  "<strong> LiftingSchemaMapping:</strong>"+
                        "<a rel="+asp+"lifting"+asp+" href="+asp+ outputSemantico.getLifting().toString()+asp+">" +
                        outputSemantico.getLifting().toString()+"</a>  </br>";
                semanticoutput+=lifting;
                Log.d(TAG, "outputSemantico Semantic" + outputSemantico.getLifting().toString());
            }

        }




    }

   }