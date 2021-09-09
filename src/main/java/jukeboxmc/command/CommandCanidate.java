package jukeboxmc.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jukeboxmc.command.annotation.ParameterSection;

import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class CommandCanidate {

    private ParameterSection overload;
    private Map<String, Object> arguments;
    private boolean completedOptionals;
    private boolean readCompleted;

}
