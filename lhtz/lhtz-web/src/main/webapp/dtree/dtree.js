/*--------------------------------------------------
	dTree 2.05

	
---------------------------------------------------*/

// Node object
function Node(id, pid, name, url, title, target, icon, iconOpen, open) {
	this.id = id;
	this.pid = pid;
	this.name = name;
	this.url = url;
	this.title = title;
	this.target = target;
	this.icon = icon;
	this.iconOpen = iconOpen;
	this._io = open || false;
	this._is = false;
	this._ls = false;
	this._hc = true;
	this._ai = 0;
	this._p;
};

/*** dTree  ** Edited by smallnetvisitor.* * objName: name of dTree object.* targetID: the id of your container,which you used to display the tree* type: which kind of category are you doing with ? It must be one of these  "goods" , "vendor" and "consumer" ,this is just for smallnetvisitor's Sales Management System.**/
function dTree(objName,targetID,path) {
	
	this.config = {
		
		target				: null,
		
		// smallnetvisitor changed it to be false.
		folderLinks			: true,//
		useSelection		: true,
		useCookies			: true,
		useLines				: true,
		useIcons				: true,
		useStatusText		: false,
		closeSameLevel	: true,
		inOrder				: false
	}
			// smallnetvisitor changed this to his own path.
	this.icon =		{
			
		root				: path+'/dtree/img/base.gif',				folder			    : path+'/dtree/img/folder.gif',				folderOpen	        : path+'/dtree/img/folderopen.gif',
		node				: path+'/dtree/img/page.gif',
		empty				: path+'/dtree/img/empty.gif',
		line				: path+'/dtree/img/line.gif',
		join				: path+'/dtree/img/join.gif',
		joinBottom	: path+'/dtree/img/joinbottom.gif',
		plus				: path+'/dtree/img/plus.gif',
		plusBottom	: path+'/dtree/img/plusbottom.gif',
		minus				: path+'/dtree/img/minus.gif',
		minusBottom	: path+'/dtree/img/minusbottom.gif',
		nlPlus			: path+'/dtree/img/nolines_plus.gif',
		nlMinus			: path+'/dtree/img/nolines_minus.gif'
	};
	
	this.obj = objName;
	this.aNodes = [];
	
	this.aNodesData=[];	//This array save the original data all the time.
	this.targetID=targetID||'dtree';	// Tree will be displayed in this container.	
	
	this.aIndent = [];
	this.root = new Node(-1);
	this.selectedNode = null;
	this.selectedFound = false;
	this.completed = false;
};
// Adds a new node to the node array
dTree.prototype.add = function(id, pid, name, url, title, target, icon, iconOpen, open) {
	
	// Add by smallnetvisitor.
	this.completed = false;
	
	this.aNodesData[this.aNodesData.length] = new Node(id, pid, name, url, title, target, icon, iconOpen, open);
};

// Open/close all nodes
dTree.prototype.openAll = function() {
	
	this.oAll(true);
};
	
dTree.prototype.closeAll = function() {
	
	this.oAll(false);
};
//node.icon = icon.nodedTree.prototype.setIconNode = function(pid,icon){	for(var i=0;i<this.aNodesData.length;i++){	     var tmp=this.aNodesData[i];	     if(tmp.id==pid){	         //this.aNodesData.remove(i);		     tmp.icon = icon;		     tmp.iconOpen = icon;		     this.aNodesData[i]=tmp;	     }	}}// Add by smallnetvisitor .// get child nodes from web server via AJAX automatically // pid : parentID.dTree.prototype.getChildren = function(pid){		var ajax = null;   	if (window.ActiveXObject) {			try{					ajax = new ActiveXObject("Microsoft.XMLHTTP");					}catch(e){					alert("fail to create ActiveXObject.");		}			} else {			if (window.XMLHttpRequest) {						try{								ajax = new XMLHttpRequest();							}catch(e){							alert("fail to create XMLHttpRequest.");			}					}	}		// following is just for smallnetvisitor's Sales Management System. 	// Server gives me a message like this : id1,name1,childCount1|id2,name2,childCount2|...	// var url ="/servlet/category?action=getChildren&parentID=" + pid +"&type=" + this.type;		var url=path+"/configTreeServlet?path="+pid;		// use this to get this dTree object in the following function.	var tree=this;	//alert(tree.aNodesData.length);	for(var i=0;i<tree.aNodesData.length;i++){	     var node=tree.aNodesData[i];	     // alert(node.pid+"|"+pid);	     if(node.pid==pid){	       		     tree.aNodesData.remove(i);		     //tree.aNodes.remove(i)		     i--;	     }	     //alert(i);	}	if (tree.config.useCookies) tree.updateCookie();	//alert(tree.aNodesData.length);			ajax.onreadystatechange = function () {	  		if (ajax.readyState == 4&&ajax.status == 200) {			 			if(ajax.responseText=="false") 			{						  tree.setIconNode(pid,tree.icon.node);			  return;			}			//alert(ajax.responseText);			if(ajax.responseText==""){			//alert(ajax.responseText);			    tree.setIconNode(pid,tree.icon.node);			}			var categories=ajax.responseText.split('|');			 /*	if(pid!=0){	for(var i=0;i<tree.aNodesData.length;i++){	     alert(tree.aNodesData[i].id+"-"+tree.aNodes[i].id);	}	}*/									for(var i=0;i<categories.length;i++){							var aCat = categories[i].split(',');								if(aCat.length==2){										var id=aCat[0];					var name=aCat[1];										tree.add(id, pid, name, path+"/configcenter/view.htm?path="+id, name,"main");				}			}												tree.show();						tree.openTo(pid);		}			};		ajax.open("POST",url);		ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	ajax.send(null);	}/**/dTree.prototype.showCategory = function(){}// Add by smallnetvisitor.
// Call to show the tree .
dTree.prototype.show = function(){
	
	// Renew the two array to save original data.
	this.aNodes=new Array();
	this.aIndent=new Array();

	// Dump original data to aNode array.
	for(var i=0 ; i<this.aNodesData.length ; i++){
		
		var oneNode=this.aNodesData[i];

		this.aNodes[i]=new Node(oneNode.id,oneNode.pid,oneNode.name,oneNode.url,oneNode.title,oneNode.target,oneNode.icon,oneNode.iconOpen,oneNode.open);
	}	//alert(this.aNodesData.length+"-"+this.aNodes.length);
	this.rewriteHTML();
}

// Outputs the tree to the page , callled by show()
// Changed by smallnetvisitor.
dTree.prototype.rewriteHTML = function() {
	
	var str = '';
	
	// Added by smallnetvisitor. 
	var targetDIV;
	targetDIV=document.getElementById(this.targetID);
	
	if(!targetDIV){
		
		alert('dTree can\'t find your specified container to show your tree.\n\n Please check your code!');

		return;
	}
	
	if (this.config.useCookies) this.selectedNode = this.getSelected();
	
	str += this.addNode(this.root);
		

	// Disabled by smallnetvisitor.
	//	str += '</div>';
	if (!this.selectedFound) this.selectedNode = null;
	this.completed = true;
	
	// Disabled and added by smallnetvisitor.
	//return str;
	targetDIV.innerHTML=str;
	
};

// Creates the tree structure/**add invoke addNode to structrue the tree html  */
dTree.prototype.addNode = function(pNode) {
	
	var str = '';
	var n=0;
	if (this.config.inOrder) n = pNode._ai;
	for (n; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == pNode.id) {
			var cn = this.aNodes[n];
			cn._p = pNode;
			cn._ai = n;
			this.setCS(cn);
			if (!cn.target && this.config.target) cn.target = this.config.target;
			if (cn._hc && !cn._io && this.config.useCookies) cn._io = this.isOpen(cn.id);
			if (!this.config.folderLinks && cn._hc) cn.url = null;
			if (this.config.useSelection && cn.id == this.selectedNode && !this.selectedFound) {
					cn._is = true;
					this.selectedNode = n;
					this.selectedFound = true;
			}
			str += this.node(cn, n);
			if (cn._ls) break;
		}
	}//alert(str);
	return str;
};


// Creates the node icon, url and text/**nodeΪnode*nodeId*/
dTree.prototype.node = function(node, nodeId) {
	
	var str = '<div class="dTreeNode">' + this.indent(node, nodeId);
	if (this.config.useIcons) {		if (!node.icon){ 				node.icon = (this.root.id == node.pid) ? this.icon.root : ((node._hc) ? this.icon.folder : this.icon.node);        }       // alert(node.icon);      // alert(node._hc);
		if (!node.iconOpen) node.iconOpen = (node._hc) ? this.icon.folderOpen : this.icon.node;        if(node.pid==-1){           node.iconOpen =this.icon.root;        }
		str += '<img id="i' + this.obj + nodeId + '" src="' + ((node._io) ? node.iconOpen : node.icon) + '" alt="" />';
	}
	if (node.url) {
		str += '<a id="s' + this.obj + nodeId + '" class="' + ((this.config.useSelection) ? ((node._is ? 'nodeSel' : 'node')) : 'node') + '" href="' + node.url + '"';
		if (node.title) str += ' title="' + node.title + '"';
		if (node.target) str += ' target="' + node.target + '"';
		if (this.config.useStatusText) str += ' onmouseover="window.status=\'' + node.name + '\';return true;" onmouseout="window.status=\'\';return true;" ';
		if (this.config.useSelection && ((node._hc && this.config.folderLinks) || !node._hc))
			str += ' onclick="javascript: ' + this.obj + '.s(' + nodeId + ');"';
		str += '>';
	}
	else if ((!this.config.folderLinks || !node.url) && node._hc && node.pid != this.root.id)
		str += '<a href="javascript: ' + this.obj + '.o(' + nodeId + ');" class="node">';
	str += node.name;
	if (node.url || ((!this.config.folderLinks || !node.url) && node._hc)) str += '</a>';
	str += '</div>';
	if (node._hc) {
		str += '<div id="d' + this.obj + nodeId + '" class="clip" style="display:' + ((this.root.id == node.pid || node._io) ? 'block' : 'none') + ';">';
		str += this.addNode(node);
		str += '</div>';
	}
	this.aIndent.pop();
	return str;
};


// Adds the empty and line icons
dTree.prototype.indent = function(node, nodeId) {
	
	var str = '';
	if (this.root.id != node.pid) {
		for (var n=0; n<this.aIndent.length; n++){		
			str += '<img src="' + ( (this.aIndent[n] == 1 && this.config.useLines) ? this.icon.line : this.icon.empty ) + '" alt="" />';        }
		(node._ls) ? this.aIndent.push(0) : this.aIndent.push(1);
		if (node._hc) {
			str += '<a href="javascript: ' + this.obj + '.o(' + nodeId + ');"><img id="j' + this.obj + nodeId + '" src="';
			if (!this.config.useLines) str += (node._io) ? this.icon.nlMinus : this.icon.nlPlus;
			else str += ( (node._io) ? ((node._ls && this.config.useLines) ? this.icon.minusBottom : this.icon.minus) : ((node._ls && this.config.useLines) ? this.icon.plusBottom : this.icon.plus ) );
			str += '" alt="" /></a>';
		} else {		    str += '<img src="' + ( (this.config.useLines) ? ((node._ls) ? this.icon.joinBottom : this.icon.join ) : this.icon.empty) + '" alt="" />';		}
	}
	return str;
};


// Checks if a node has any children and if it is the last sibling
dTree.prototype.setCS = function(node) {

	var lastId;

	for (var n=0; n<this.aNodes.length; n++) {
	
		if (this.aNodes[n].pid == node.id) node._hc = true;
		if (this.aNodes[n].pid == node.pid) lastId = this.aNodes[n].id;
	}

	if (lastId==node.id) node._ls = true;
};


// Returns the selected node
dTree.prototype.getSelected = function() {
	
	var sn = this.getCookie('cs' + this.obj);
	return (sn) ? sn : null;
};

// Highlights the selected node
dTree.prototype.s = function(id) {
	
	if (!this.config.useSelection) return;
	var cn = this.aNodes[id];
	if (cn._hc && !this.config.folderLinks) return;
	
};

// Toggle Open or close
dTree.prototype.o = function(id) {
	//getChildren    
	var cn = this.aNodes[id];	//this.getChildren(cn.id);	//return;
	this.nodeStatus(!cn._io, id, cn._ls);	//alert("");
	cn._io = !cn._io;   if(cn._io){	  this.getChildren(cn.id);	}else{
	if (this.config.closeSameLevel) this.closeLevel(cn);
	if (this.config.useCookies) this.updateCookie();  }
};

// Open or close all nodes
dTree.prototype.oAll = function(status) {
	
	for (var n=0; n<this.aNodes.length; n++) {
		
		if (this.aNodes[n]._hc && this.aNodes[n].pid != this.root.id) {
			
			this.nodeStatus(status, n, this.aNodes[n]._ls)
			this.aNodes[n]._io = status;
		}
	}
			
	if (this.config.useCookies) this.updateCookie();
};


// Opens the tree to a specific node
dTree.prototype.openTo = function(nId, bSelect, bFirst) {
	
	if (!bFirst) {
		
		for (var n=0; n<this.aNodes.length; n++) {
			
			if (this.aNodes[n].id == nId) {
				
				nId=n;
				break;
			}
		}
	}
				
	var cn=this.aNodes[nId];
	if (cn.pid==this.root.id || !cn._p) return;
	cn._io = true;
	cn._is = bSelect;
	if (this.completed && cn._hc) this.nodeStatus(true, cn._ai, cn._ls);
	if (this.completed && bSelect) this.s(cn._ai);
	else if (bSelect) this._sn=cn._ai;
	this.openTo(cn._p._ai, false, true);
};

				
// Closes all nodes on the same level as certain node
dTree.prototype.closeLevel = function(node) {
	
	for (var n=0; n<this.aNodes.length; n++) {
		
		if (this.aNodes[n].pid == node.pid && this.aNodes[n].id != node.id && this.aNodes[n]._hc) {
			
			this.nodeStatus(false, n, this.aNodes[n]._ls);
			this.aNodes[n]._io = false;
			this.closeAllChildren(this.aNodes[n]);
		}
	}
}

// Closes all children of a node
dTree.prototype.closeAllChildren = function(node) {
	
	for (var n=0; n<this.aNodes.length; n++) {
		
		if (this.aNodes[n].pid == node.id && this.aNodes[n]._hc) {
			
			if (this.aNodes[n]._io) this.nodeStatus(false, n, this.aNodes[n]._ls);
			this.aNodes[n]._io = false;
			this.closeAllChildren(this.aNodes[n]);		
		}
	}
}

// Change the status of a node(open or closed)
dTree.prototype.nodeStatus = function(status, id, bottom) {
	
	eDiv	= document.getElementById('d' + this.obj + id);
	eJoin	= document.getElementById('j' + this.obj + id);
	
	if (this.config.useIcons) {
		
		eIcon	= document.getElementById('i' + this.obj + id);
		eIcon.src = (status) ? this.aNodes[id].iconOpen : this.aNodes[id].icon;
	}
		
	eJoin.src = (this.config.useLines)?
	((status)?((bottom)?this.icon.minusBottom:this.icon.minus):((bottom)?this.icon.plusBottom:this.icon.plus)):
	((status)?this.icon.nlMinus:this.icon.nlPlus);
	eDiv.style.display = (status) ? 'block': 'none';
};


// [Cookie] Clears a cookie
dTree.prototype.clearCookie = function() {
	
	var now = new Date();
	var yesterday = new Date(now.getTime() - 1000 * 60 * 60 * 24);
	this.setCookie('co'+this.obj, 'cookieValue', yesterday);
	this.setCookie('cs'+this.obj, 'cookieValue', yesterday);
};

	
// [Cookie] Sets value in a cookie
dTree.prototype.setCookie = function(cookieName, cookieValue, expires, path, domain, secure) {
	
	document.cookie =
		escape(cookieName) + '=' + escape(cookieValue)
		+ (expires ? '; expires=' + expires.toGMTString() : '')
		+ (path ? '; path=' + path : '')
		+ (domain ? '; domain=' + domain : '')
		+ (secure ? '; secure' : '');
};

	
// [Cookie] Gets a value from a cookie
dTree.prototype.getCookie = function(cookieName) {
	
	var cookieValue = '';
	var posName = document.cookie.indexOf(escape(cookieName) + '=');
	
	if (posName != -1) {
		
		var posValue = posName + (escape(cookieName) + '=').length;
		var endPos = document.cookie.indexOf(';', posValue);
		if (endPos != -1) cookieValue = unescape(document.cookie.substring(posValue, endPos));
		else cookieValue = unescape(document.cookie.substring(posValue));
	}
		
	return (cookieValue);
};


// [Cookie] Returns ids of open nodes as a string
dTree.prototype.updateCookie = function() {
	
	var str = '';
	for (var n=0; n<this.aNodes.length; n++) {
		
		if (this.aNodes[n]._io && this.aNodes[n].pid != this.root.id) {
			if (str) str += '.';
			str += this.aNodes[n].id;
		}
	}
		
	this.setCookie('co' + this.obj, str);
};


// [Cookie] Checks if a node id is in a cookie
dTree.prototype.isOpen = function(id) {
	
	var aOpen = this.getCookie('co' + this.obj).split('.');
	
	for (var n=0; n<aOpen.length; n++)
		if (aOpen[n] == id) return true;
	return false;
};
dTree.prototype.remove=function(id){    if(!this.hasChildren(id)){        for(var i=0 ; i<this.aNodesData.length ; i++){            if(this.aNodesData[i].id==id){                this.aNodesData.remove(i);            }        }    }}

// If Push and pop is not implemented by the browser
if (!Array.prototype.push) {
	
	Array.prototype.push = function array_push() {
		
		for(var i=0;i<arguments.length;i++)
			this[this.length]=arguments[i];
		return this.length;
	}
};


if (!Array.prototype.pop) {
	
	Array.prototype.pop = function array_pop() {
		
		lastElement = this[this.length-1];
		this.length = Math.max(this.length-1,0);
		return lastElement;
	}
};if (!Array.prototype.remove) {Array.prototype.remove=function(dx) {  if(isNaN(dx)||dx>this.length){	  	  return false;	  	  }for(var i=0,n=0;i<this.length;i++){if(this[i]!=this[dx]){this[n++]=this[i];}}this.length-=1;}};