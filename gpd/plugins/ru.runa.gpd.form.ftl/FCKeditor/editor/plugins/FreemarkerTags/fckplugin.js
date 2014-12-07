
var FTL_PLUGIN_NAME = 'FreemarkerTags' ;
var FTL_METHOD_CMD = 'FreemarkerMethod' ;
var VISUAL_ELEMENT = 'img' ;

// Register the related command.
FCKCommands.RegisterCommand( FTL_METHOD_CMD, new FCKDialogCommand( FTL_METHOD_CMD, FCKLang.MethodTitle, "/editor/FreemarkerTags.java?method=GetAllMethods", 500, 300 ) ) ;

// Create the "FTLTag" toolbar button.
var methodItem = new FCKToolbarButton( FTL_METHOD_CMD, FCKLang.MethodTitle ) ;
methodItem.IconPath = FCKPlugins.Items[FTL_PLUGIN_NAME].Path + 'toolbar.gif' ;
FCKToolbarItems.RegisterItem( FTL_METHOD_CMD, methodItem ) ;

var FreemarkerTags = new Object() ;

// Add method
FreemarkerTags.AddMethod = function( tagName, params ) {
	var oSpan = FCK.CreateElement( VISUAL_ELEMENT );
	this.SetupSpan( oSpan, tagName, params );
	oSpan.setAttribute("ftltagparams", params);
}

// Add format
FreemarkerTags.AddFormat = function( tagName, format ) {
	var oSpan = FCK.CreateElement( VISUAL_ELEMENT );

	this.SetupSpan( oSpan, tagName, format );
	oSpan.setAttribute("ftltagformat", format);
}

FreemarkerTags.SetupSpan = function( span, tagName, tagParams ) {
	span.setAttribute("src", "http://localhost:48780/editor/FreemarkerTags.java?method=GetTagImage&tagName=" + tagName + "&tagParams=" + tagParams);
	span.setAttribute("ftltagname", tagName);
	// To avoid it to be resized.
	span.onresizestart = function() {
		FCK.EditorWindow.event.returnValue = false ;
		return false ;
	}
}

// On Gecko we must do this trick so the user select all the SPAN when clicking on it.
FreemarkerTags._SetupClickListener = function() {
	FreemarkerTags._ClickListener = function( e ) {
		if ( e.target.ftlTagName ) {
			FCKSelection.SelectNode( e.target ) ;
		}
	}
	FCK.EditorDocument.addEventListener( 'click', FreemarkerTags._ClickListener, true ) ;
}

FCK.ContextMenu.RegisterListener( { AddItems : function( menu, tag, tagName ) {
	if ( tagName && tagName.toLowerCase() == VISUAL_ELEMENT ) {
		menu.AddSeparator();
	}
	if ( tag && tag.getAttribute("ftltagparams") != null) {
		menu.AddItem( FTL_METHOD_CMD, FCKLang.MethodTitle ) ;
	}
}});

FreemarkerTags.GetParameters = function( tagName ) {
	var oXmlHttp = FCKTools.CreateXmlObject( 'XmlHttp' ) ;
	oXmlHttp.open( "GET", "/editor/FreemarkerTags.java?method=GetParameters&tagName=" + tagName, false ) ;
	oXmlHttp.send( null ) ;
	if ( oXmlHttp.status == 200 || oXmlHttp.status == 304 ) {
		return oXmlHttp.responseText ;
	} else {
		return "Error." ;
	}
}

FreemarkerTags.GetFormats = function( tagName ) {
	var oXmlHttp = FCKTools.CreateXmlObject( 'XmlHttp' ) ;
	oXmlHttp.open( "GET", "/editor/FreemarkerTags.java?method=GetFormats&tagName=" + encodeURIComponent(tagName), false ) ;
	oXmlHttp.send( null ) ;
	if ( oXmlHttp.status == 200 || oXmlHttp.status == 304 ) {
		return oXmlHttp.responseText ;
	} else {
		return "Error." ;
	}
}

FreemarkerTags.IsAvailable = function( ) {
	var oXmlHttp = FCKTools.CreateXmlObject( 'XmlHttp' ) ;
	oXmlHttp.open( "GET", "/editor/FreemarkerTags.java?method=IsAvailable", false ) ;
	oXmlHttp.send( null ) ;
	if ( oXmlHttp.status == 200 || oXmlHttp.status == 304 ) {
		return oXmlHttp.responseText ;
	} else {
		return "false";
	}
}

if (FreemarkerTags.IsAvailable() != "true") {
	FCKCommands.GetCommand( FTL_METHOD_CMD ).Execute = function() { return false; };
	FCKCommands.GetCommand( FTL_METHOD_CMD ).GetState = function() { return FCK_TRISTATE_DISABLED; } ;
}

