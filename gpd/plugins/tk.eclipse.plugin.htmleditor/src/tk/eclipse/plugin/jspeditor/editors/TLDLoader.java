package tk.eclipse.plugin.jspeditor.editors;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import jp.aonir.fuzzyxml.FuzzyXMLDocument;
import jp.aonir.fuzzyxml.FuzzyXMLElement;
import jp.aonir.fuzzyxml.FuzzyXMLNode;
import jp.aonir.fuzzyxml.FuzzyXMLParser;
import jp.aonir.fuzzyxml.XPath;

import org.eclipse.jface.preference.IPreferenceStore;

import tk.eclipse.plugin.htmleditor.HTMLPlugin;
import tk.eclipse.plugin.htmleditor.HTMLUtil;

/**
 * TLD�t�@�C�����擾���邽�߂�static���\�b�h��񋟂���N���X�B
 * �ǂݍ���TLD�t�@�C���͓����I�ɃL���b�V������܂��B
 */
public class TLDLoader {
	
	private static HashMap cache = new HashMap();
	
	/**
	 * TLD�t�@�C���̃\�[�X�𕶎���Ƃ��Ď擾���܂��B
	 * URI��http://����J�n����ꍇ�̓����[�h����A
	 * ����ȊO�̏ꍇ�̓x�[�X�f�B���N�g������̑��΃p�X�Ƃ��ă��[�J������ǂݍ��݂܂��B
	 * 
	 * @param basedir �x�[�X�f�B���N�g��
	 * @param uri TLD��URI
	 * @return TLD�̓��̓X�g���[��
	 */
	public static InputStream get(File basedir,String uri) throws Exception {
		if(cache.get(uri)==null){
			// �g�ݍ���TLD
			Map innerTLD = HTMLPlugin.getInnerTLD();
			if(innerTLD.get(uri)!=null){
				InputStream in = TLDLoader.class.getResourceAsStream((String)innerTLD.get(uri));
				byte[] bytes = HTMLUtil.readStream(in);
				cache.put(uri,bytes);
				return new ByteArrayInputStream(bytes);
			}
			// �v���t�@�����X�E�X�g�A�ɓo�^����Ă���TLD
			Map pref = getPreferenceTLD();
			if(pref.get(uri)!=null){
				InputStream in = new FileInputStream(new File((String)pref.get(uri)));
				byte[] bytes = HTMLUtil.readStream(in);
				cache.put(uri,bytes);
				return new ByteArrayInputStream(bytes);
			}
			// web.xml�ɓo�^����Ă��邩�H
			byte[] bytes = getTLDFromWebXML(basedir,uri);
			if(bytes!=null){
				cache.put(uri,bytes);
				return new ByteArrayInputStream(bytes);
			}
			if(uri.startsWith("http://")){
				// jar�t�@�C����META-INF���猟��
				bytes = getTLDFromJars(basedir,uri);
				if(bytes!=null){
					cache.put(uri,bytes);
					return new ByteArrayInputStream(bytes);
				}
				// URL����ǂݍ���(����Ȃ������c)
				URL url = new URL(uri);
				InputStream in = url.openStream();
				cache.put(uri,HTMLUtil.readStream(in));
			} else {
				// �t�@�C������ǂݍ���
				File file = new File(basedir,uri);
				InputStream in = new FileInputStream(file);
				cache.put(uri,HTMLUtil.readStream(in));
			}
		}
		
		byte[] bytes = (byte[])cache.get(uri);
		return new ByteArrayInputStream(bytes);
	}
	
	/** �v���t�@�����X�X�g�A����ݒ���e��ǂݍ��� */
	private static Map getPreferenceTLD(){
		HashMap map = new HashMap();
		
		IPreferenceStore store = HTMLPlugin.getDefault().getPreferenceStore();
		String[] uri  = store.getString(HTMLPlugin.PREF_TLD_URI).split("\n");
		String[] path = store.getString(HTMLPlugin.PREF_TLD_PATH).split("\n");
		for(int i=0;i<uri.length;i++){
			if(!uri[i].trim().equals("") && !path[i].trim().equals("")){
				map.put(uri[i].trim(),path[i].trim());
			}
		}
		
		return map;
	}
	
	/** web.xml����ǂݍ��� */
	private static byte[] getTLDFromWebXML(File basedir,String uri){
		File webXML = new File(basedir,"/WEB-INF/web.xml");
		
		if(webXML.exists() && webXML.isFile()){
			try {
				FuzzyXMLDocument doc = new FuzzyXMLParser().parse(new FileInputStream(webXML));
				FuzzyXMLNode[] nodes = XPath.selectNodes(doc.getDocumentElement(),"/web-app/taglib|/web-app/jsp-config/taglib");
				
				for(int i=0;i<nodes.length;i++){
					FuzzyXMLElement element = (FuzzyXMLElement)nodes[i];
					String taglibUri = HTMLUtil.getXPathValue(element,"/taglib-uri/child::text()");
					String taglibLoc = HTMLUtil.getXPathValue(element,"/taglib-location/child::text()");
					if(uri.equals(taglibUri)){
						if(taglibLoc!=null && taglibLoc.endsWith(".tld")){
							File file = new File(basedir,taglibLoc);
							return HTMLUtil.readStream(new FileInputStream(file));
						}
						break;
					}
				}
			} catch(Exception ex){
				HTMLPlugin.logException(ex);
			}
		}
		return null;
	}
	
	/** jar��META-INF����ǂݍ��� */
	private static byte[] getTLDFromJars(File basedir,String uri){
		File lib = new File(basedir,"/WEB-INF/lib");
		if(lib.exists() && lib.isDirectory()){
			File[] files = lib.listFiles();
			try {
				for(int i=0;i<files.length;i++){
					if(files[i].getName().endsWith(".jar")){
						JarFile jarFile = new JarFile(files[i]);
						Enumeration e = jarFile.entries();
						while(e.hasMoreElements()){
							JarEntry entry = (JarEntry)e.nextElement();
							if(entry.getName().endsWith(".tld")){
								byte[] bytes = HTMLUtil.readStream(jarFile.getInputStream(entry));
								try {
									FuzzyXMLDocument doc = new FuzzyXMLParser().parse(new ByteArrayInputStream(bytes));
									String nodeURI = HTMLUtil.getXPathValue(doc.getDocumentElement(),"/taglib/uri/child::text()");
									if(nodeURI!=null && uri.equals(nodeURI)){
										return bytes;
									}
								} catch(Exception ex){
									HTMLPlugin.logException(ex);
								}
							}
						}
					}
				}
			} catch(Exception ex){
				HTMLPlugin.logException(ex);
			}
		}
		return null;
	}
}
