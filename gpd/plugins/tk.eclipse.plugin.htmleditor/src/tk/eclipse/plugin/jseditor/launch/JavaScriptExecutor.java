package tk.eclipse.plugin.jseditor.launch;
import java.io.File;
import java.io.FileReader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * 
 * @author Naoki Takezoe
 */
public class JavaScriptExecutor {
	
	public static void main(String[] args) throws Exception {
		File file = new File(args[0]);
		
		Context cx = Context.enter();
		Scriptable scope = cx.initStandardObjects();

		try {
			if(args.length > 1){
				for(int i=1; i<args.length; i++){
					File includeFile = new File(args[i]);
					cx.evaluateReader(scope, new FileReader(includeFile), includeFile.getName(), 1, null);
				}
			}
			
			cx.evaluateReader(scope, new FileReader(file), file.getName(), 1, null);
			
		} finally {
			Context.exit();
		}

	}
	
}
