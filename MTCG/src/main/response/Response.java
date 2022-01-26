package response;

import Http.HttpStatus;

import java.io.BufferedWriter;
import java.io.IOException;

public interface Response {
    final Responsebuilder respond = Responsebuilder.getInstance();
}
