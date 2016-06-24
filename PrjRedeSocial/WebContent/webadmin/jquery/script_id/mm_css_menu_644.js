function MenuObject(){

this.mmOpenContainer = null;
this.mmOpenMenus = null;
this.mmHideMenuTimer = null;
this.menuOpen = 0;
this.menuOpen2 = 0;
this.menuOpen3 = 0;

this.MM_menuStartTimeout = function(hideTimeout) {
	this.mmHideMenuTimer = setTimeout(this.MM_menuHideMenus(), hideTimeout);	
}

this.MM_menuHideMenus = function() {
	this.MM_menuResetTimeout();
	if(this.mmOpenContainer) {
		var c = document.getElementById(this.mmOpenContainer);
		c.style.visibility = "inherit";
		this.mmOpenContainer = null;
	}
	if( this.mmOpenMenus ) {
		for(var i = 0; i < this.mmOpenMenus.length ; i++) {
			var m = document.getElementById(this.mmOpenMenus[i]);
			m.style.visibility = "hidden";			
		}
		this.mmOpenMenus = null;
	}
}

this.MM_menuHideSubmenus = function(menuName) {
	if( this.mmOpenMenus ) {
		var h = false;
		var c = 0;
		for(var i = 0; i < this.mmOpenMenus.length ; i++) {
			if( h ) {
				var m = document.getElementById(this.mmOpenMenus[i]);
				m.style.visibility = "hidden";
			} else if( this.mmOpenMenus[i] == menuName ) {
				h = true;
			} else {
				c++;
			}
		}
		this.mmOpenMenus.length = c+1;
	}
}

this.MM_menuOverMenuItem = function(menuName, subMenuSuffix) {
	this.MM_menuResetTimeout();
	this.MM_menuHideSubmenus(menuName);
	if( subMenuSuffix ) {
		var subMenuName = "" + menuName + "_" + subMenuSuffix;
		this.MM_menuShowSubMenu(subMenuName);
	}
}

this.MM_menuShowSubMenu = function(subMenuName) {
	this.MM_menuResetTimeout();
	var e = document.getElementById(subMenuName);
	e.style.visibility = "inherit";
	if( !this.mmOpenMenus ) {
		this.mmOpenMenus = new Array;
	}
	this.mmOpenMenus[this.mmOpenMenus.length] = "" + subMenuName;
}

this.MM_menuResetTimeout = function() {
	if (this.mmHideMenuTimer) clearTimeout(this.mmHideMenuTimer);
	this.mmHideMenuTimer = null;
}

this.MM_menuShowMenu = function(containName, menuName, xOffset, yOffset, triggerName, menuSelect) {
  if (menuSelect == 1){
    if (this.menuOpen == 0){
	this.MM_menuHideMenus();
	this.MM_menuResetTimeout();
	this.MM_menuShowMenuContainer(containName, xOffset, yOffset, triggerName);
	this.MM_menuShowSubMenu(menuName);
        this.menuOpen = 1;
    }else{
        this.MM_nbGroup('out');
        this.menuOpen = 0;
        this.MM_menuStartTimeout(0);
    }
  }
  else if (menuSelect == 2){
    if (this.menuOpen2 == 0){
	this.MM_menuHideMenus();
	this.MM_menuResetTimeout();
	this.MM_menuShowMenuContainer(containName, xOffset, yOffset, triggerName);
	this.MM_menuShowSubMenu(menuName);
        this.menuOpen2 = 1;
    }else{
        this.MM_nbGroup('out');
        this.menuOpen2 = 0;
        this.MM_menuStartTimeout(0);
    }
  }
  else if (menuSelect == 3){
    if (this.menuOpen3 == 0){
	this.MM_menuHideMenus();
	this.MM_menuResetTimeout();
	this.MM_menuShowMenuContainer(containName, xOffset, yOffset, triggerName);
	this.MM_menuShowSubMenu(menuName);
        this.menuOpen3 = 1;
    }else{
        this.MM_nbGroup('out');
        this.menuOpen3 = 0;
        this.MM_menuStartTimeout(0);
    }
  }
}

this.MM_menuShowMenuContainer = function(containName, x, y, triggerName) {	
	var c = document.getElementById(containName);
	var s = c.style;
	s.visibility = "inherit";
	
	this.mmOpenContainer = "" + containName;
}

/* new */
this.MM_findObj = function(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

this.MM_nbGroup = function(event, grpName) { //v6.0
var i,img,nbArr,args=this.MM_nbGroup.arguments;
  if (event == "init" && args.length > 2) {
    if ((img = this.MM_findObj(args[2])) != null && !img.MM_init) {
      img.MM_init = true; img.MM_up = args[3]; img.MM_dn = img.src;
      if ((nbArr = document[grpName]) == null) nbArr = document[grpName] = new Array();
      nbArr[nbArr.length] = img;
      for (i=4; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {
        if (!img.MM_up) img.MM_up = img.src;
        img.src = img.MM_dn = args[i+1];
        nbArr[nbArr.length] = img;
    } }
  } else if (event == "over") {
    document.MM_nbOver = nbArr = new Array();
    for (i=1; i < args.length-1; i+=3) if ((img = this.MM_findObj(args[i])) != null) {
      if (!img.MM_up) img.MM_up = img.src;
      img.src = (img.MM_dn && args[i+2]) ? args[i+2] : ((args[i+1])?args[i+1] : img.MM_up);
      nbArr[nbArr.length] = img;
    }
  } else if (event == "out" ) {
      //for (i=0; i < document.MM_nbOver.length; i++) {
      //  img = document.MM_nbOver[i]; img.src = (img.MM_dn) ? img.MM_dn : img.MM_up; 
      //}
  } else if (event == "down") {
    nbArr = document[grpName];
    if (nbArr) for (i=0; i < nbArr.length; i++) { img=nbArr[i]; img.src = img.MM_up; img.MM_dn = 0; }
    document[grpName] = nbArr = new Array();
    for (i=2; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {
      if (!img.MM_up) img.MM_up = img.src;
      img.src = img.MM_dn = (args[i+1])? args[i+1] : img.MM_up;
      nbArr[nbArr.length] = img;
  } }
}

this.MM_preloadImages = function() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=this.MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

this.teste = function(testeStr){
  alert(testeStr);
}

}

var objeto1 = new MenuObject();
var objeto2 = new MenuObject();
var objeto3 = new MenuObject();

function show_menu1(containName, menuName, xOffset, yOffset, triggerName){
  //objeto1.teste("1");
  if (objeto2.menuOpen2 == 1){
    objeto2.MM_menuShowMenu(containName, menuName, xOffset, yOffset, triggerName, 2);
  }
  objeto1.MM_menuShowMenu(containName, menuName, xOffset, yOffset, triggerName, 1);
}

function show_menu2(containName, menuName, xOffset, yOffset, triggerName){
  //objeto2.teste("2");
  if (objeto1.menuOpen == 1){
    objeto1.MM_menuShowMenu(containName, menuName, xOffset, yOffset, triggerName, 1);
  }
  objeto2.MM_menuShowMenu(containName, menuName, xOffset, yOffset, triggerName, 2);
}

function show_menu3(containName, menuName, xOffset, yOffset, triggerName){
  objeto3.MM_menuShowMenu(containName, menuName, xOffset, yOffset, triggerName, 3);
}