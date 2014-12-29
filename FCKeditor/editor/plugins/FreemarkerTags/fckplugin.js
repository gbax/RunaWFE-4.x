
var FTL_PLUGIN_NAME = 'FreemarkerTags' ;
var FTL_METHOD_CMD = 'FreemarkerMethod' ;
var VISUAL_ELEMENT = 'img' ;


var FreemarkerTags = new Object() ;

FreemarkerTags.TagDialog = function() {
  var oXmlHttp = (!window.XMLHttpRequest)? new ActiveXObject("Microsoft.XMLHTTP") : new XMLHttpRequest();
  oXmlHttp.open( "GET", "/editor/FreemarkerTags.java?method=TagDialog&componentId=" + FreemarkerTags.SelectedId.id, false ) ;
  oXmlHttp.send( null ) ;
  if ( oXmlHttp.status == 200 || oXmlHttp.status == 304 ) {
    return oXmlHttp.responseText ;
  } else {
    return "Error." ;
  }
};

var FCKTagDialogCommand = function(name)
{	
	this.Name = name;
}

FCKTagDialogCommand.prototype.Execute = function()
{	
	FreemarkerTags.TagDialog();
}

FCKTagDialogCommand.prototype.GetState = function()
{
	FCK.Focus();
    return 0;
}

// Register the related command.
FCKCommands.RegisterCommand( FTL_METHOD_CMD, new FCKTagDialogCommand(FTL_METHOD_CMD)) ;

// Create the "FTLTag" toolbar button.
var methodItem = new FCKToolbarButton( FTL_METHOD_CMD, FCKLang.MethodTitle ) ;
methodItem.IconPath = FCKPlugins.Items[FTL_PLUGIN_NAME].Path + 'toolbar.gif' ;
FCKToolbarItems.RegisterItem( FTL_METHOD_CMD, methodItem ) ;


FreemarkerTags.SelectedId = {id : -1};

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
	span.setAttribute("src", "http://localhost:48780/editor/FreemarkerTags.java?method=GetTagImage&tagName=" + tagName + "&tagParams=" + encodeURIComponent(tagParams));
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

FreemarkerTags.ComponentSelected = function( componentId ) {
  FreemarkerTags.SelectedId.id = componentId;
  var oXmlHttp = (!window.XMLHttpRequest)? new ActiveXObject("Microsoft.XMLHTTP") : new XMLHttpRequest();
  oXmlHttp.open( "GET", "/editor/FreemarkerTags.java?method=ComponentSelected&componentId=" + componentId, false ) ;
  oXmlHttp.send( null ) ;
  if ( oXmlHttp.status == 200 || oXmlHttp.status == 304 ) {
    return oXmlHttp.responseText ;
  } else {
    return "Error." ;
  }
};

FreemarkerTags.ComponentDeselected = function() {
  FreemarkerTags.SelectedId.id = -1;
  var oXmlHttp = (!window.XMLHttpRequest)? new ActiveXObject("Microsoft.XMLHTTP") : new XMLHttpRequest();
  oXmlHttp.open( "GET", "/editor/FreemarkerTags.java?method=ComponentDeselected", false ) ;
  oXmlHttp.send( null ) ;
  if ( oXmlHttp.status == 200 || oXmlHttp.status == 304 ) {
    return oXmlHttp.responseText ;
  } else {
    return "Error." ;
  }
};



