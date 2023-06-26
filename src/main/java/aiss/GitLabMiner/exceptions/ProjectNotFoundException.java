package aiss.GitLabMiner.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND,reason = "Id not found")
public class ProjectNotFoundException extends Exception{
}
