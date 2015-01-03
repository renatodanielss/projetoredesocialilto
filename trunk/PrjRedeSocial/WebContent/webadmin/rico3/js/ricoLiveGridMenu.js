if(typeof Rico=='undefined')
  throw("GridMenu requires the Rico JavaScript framework");


/**
 * Standard menu for LiveGrid
 */
Rico.GridMenu = function(options) {
  this.initialize(options);
};

Rico.GridMenu.prototype = {

initialize: function(options) {
  this.options = {
    width           : '18em',
    dataMenuHandler : null          // put custom items on the menu
  };
  Rico.extend(this.options, options || {});
  Rico.extend(this, new Rico.Menu(this.options));
  this.sortmenu = new Rico.Menu({ width: '15em' });
  this.filtermenu = new Rico.Menu({ width: '22em' });
  this.exportmenu = new Rico.Menu({ width: '24em' });
  this.hideshowmenu = new Rico.Menu({ width: '22em' });
  this.createDiv();
  this.sortmenu.createDiv();
  this.filtermenu.createDiv();
  this.exportmenu.createDiv();
  this.hideshowmenu.createDiv();
},

// Build context menu for grid
buildGridMenu: function(r,c) {
  this.clearMenu();
  var totrows=this.liveGrid.buffer.totalRows;
  var onBlankRow=r >= totrows;
  var column=this.liveGrid.columns[c];
  if (this.options.dataMenuHandler) {
     var showDefaultMenu=this.options.dataMenuHandler(this.liveGrid,r,c,onBlankRow);
     if (!showDefaultMenu) return (this.itemCount > 0);
  }

  // menu items for sorting
  if (column.sortable && totrows>0) {
    this.sortmenu.clearMenu();
    this.addSubMenuItem(Rico.getPhraseById("gridmenuSortBy",column.displayName), this.sortmenu, false);
    this.sortmenu.addMenuItemId("gridmenuSortAsc", Rico.bind(column,'sortAsc'), true);
    this.sortmenu.addMenuItemId("gridmenuSortDesc", Rico.bind(column,'sortDesc'), true);
  }

  // menu items for filtering
  this.filtermenu.clearMenu();
  if (column.canFilter() && !column.format.filterUI && (!onBlankRow || column.filterType == Rico.TableColumn.USERFILTER)) {
    this.addSubMenuItem(Rico.getPhraseById("gridmenuFilterBy",column.displayName), this.filtermenu, false);
    column.userFilter=column.getValue(r);
    if (column.filterType == Rico.TableColumn.USERFILTER) {
      this.filtermenu.addMenuItemId("gridmenuRemoveFilter", Rico.bind(column,'setUnfiltered',false), true);
      if (column.filterOp=='LIKE')
        this.filtermenu.addMenuItemId("gridmenuChgKeyword", Rico.bind(this.liveGrid,'openKeyword',c), true);
      if (column.filterOp=='NE' && !onBlankRow)
        this.filtermenu.addMenuItemId("gridmenuExcludeAlso", Rico.bind(column,'addFilterNE'), true);
    } else if (!onBlankRow) {
      this.filtermenu.addMenuItemId("gridmenuInclude", Rico.bind(column,'setFilterEQ'), true);
      this.filtermenu.addMenuItemId("gridmenuGreaterThan", Rico.bind(column,'setFilterGE'), column.userFilter!='');
      this.filtermenu.addMenuItemId("gridmenuLessThan", Rico.bind(column,'setFilterLE'), column.userFilter!='');
      if (column.isText)
        this.filtermenu.addMenuItemId("gridmenuContains", Rico.bind(this.liveGrid,'openKeyword',c), true);
      this.filtermenu.addMenuItemId("gridmenuExclude", Rico.bind(column,'setFilterNE'), true);
    }
    if (this.liveGrid.filterCount() > 0) {
      this.filtermenu.addMenuItemId("gridmenuRefresh", Rico.bind(this.liveGrid,'filterHandler'), true);
      this.filtermenu.addMenuItemId("gridmenuRemoveAll", Rico.bind(this.liveGrid,'clearFilters'), true);
    }
  } else if (this.liveGrid.filterCount() > 0) {
    this.addSubMenuItem(Rico.getPhraseById("gridmenuFilterBy",column.displayName), this.filtermenu, false);
    this.filtermenu.addMenuItemId("gridmenuRemoveAll", Rico.bind(this.liveGrid,'clearFilters'), true);
  }

  // menu items for Print/Export
  if (this.liveGrid.options.maxPrint > 0 && totrows>0) {
    this.exportmenu.clearMenu();
    this.addSubMenuItem(Rico.getPhraseById('gridmenuExport'),this.exportmenu,false);
    this.exportmenu.addMenuItemId("gridmenuExportVis2Web", Rico.bind(this.liveGrid,'printVisible','plain'));
    this.exportmenu.addMenuItemId("gridmenuExportAll2Web", Rico.bind(this.liveGrid,'printAll','plain'), this.liveGrid.buffer.totalRows <= this.liveGrid.options.maxPrint);
    if (Rico.isIE) {
      this.exportmenu.addMenuBreak();
      this.exportmenu.addMenuItemId("gridmenuExportVis2SS", Rico.bind(this.liveGrid,'printVisible','owc'));
      this.exportmenu.addMenuItemId("gridmenuExportAll2SS", Rico.bind(this.liveGrid,'printAll','owc'), this.liveGrid.buffer.totalRows <= this.liveGrid.options.maxPrint);
    }
  }

  // menu items for hide/unhide
  var hiddenCols=this.liveGrid.listInvisible();
  for (var showableCnt=0,x=0; x<hiddenCols.length; x++) {
    if (hiddenCols[x].canHideShow()) showableCnt++;
  }
  if (showableCnt > 0 || column.canHideShow()) {
    this.hideshowmenu.clearMenu();
    this.addSubMenuItem(Rico.getPhraseById('gridmenuHideShow'),this.hideshowmenu,false);
    this.hideshowmenu.addMenuItemId('gridmenuChooseCols', Rico.bind(this.liveGrid,'chooseColumns'),true,false);
    var visibleCnt=this.liveGrid.columns.length-hiddenCols.length;
    var enabled=(visibleCnt>1 && column.visible && column.canHideShow());
    this.hideshowmenu.addMenuItem(Rico.getPhraseById('gridmenuHide',column.displayName), Rico.bind(column,'hideColumn'), enabled);
    for (var cnt=0,x=0; x<hiddenCols.length; x++) {
      if (hiddenCols[x].canHideShow()) {
        if (cnt++==0) this.hideshowmenu.addMenuBreak();
        this.hideshowmenu.addMenuItem(Rico.getPhraseById('gridmenuShow',hiddenCols[x].displayName), Rico.bind(hiddenCols[x],'showColumn'), true);
      }
    }
    if (hiddenCols.length > 1)
      this.hideshowmenu.addMenuItemId('gridmenuShowAll', Rico.bind(this.liveGrid,'showAll'));
  }
  return true;
}

}

Rico.includeLoaded('ricoLiveGridMenu.js');
