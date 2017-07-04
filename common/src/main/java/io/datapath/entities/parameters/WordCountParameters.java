package io.datapath.entities.parameters;

/**
 * Created by jackson on 03/07/17.
 */
public class WordCountParameters extends TaskParameters {

    public static final String INPUT_FILE = "inputFilePath";

    @Override
    public String[] getMandatoryFields() {
        return new String[]{INPUT_FILE};
    }
}
