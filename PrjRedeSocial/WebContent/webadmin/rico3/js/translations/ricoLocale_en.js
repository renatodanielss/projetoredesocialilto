/*****************************************************************
 ricoLocale_en.js - a component of Rico 3.0
 English localization strings
 If you would like to include translations for another language, 
 please send them to dowdybrown@yahoo.com
******************************************************************/
Rico.langCode='en';

// used in ricoLiveGrid.js

Rico.addPhraseId('bookmarkExact',"Listing records $1 - $2 of $3");
Rico.addPhraseId('bookmarkAbout',"Listing records $1 - $2 of more than $3");
Rico.addPhraseId('bookmarkNoRec',"No records");
Rico.addPhraseId('bookmarkNoMatch',"No matching records");
Rico.addPhraseId('bookmarkLoading',"Loading...");
Rico.addPhraseId('sorting',"Sorting...");
Rico.addPhraseId('exportStatus',"Exporting row $1");
Rico.addPhraseId('filterAll',"(all)");
Rico.addPhraseId('filterBlank',"(blank)");
Rico.addPhraseId('filterEmpty',"(empty)");
Rico.addPhraseId('filterNotEmpty',"(not empty)");
Rico.addPhraseId('filterLike',"like: $1");
Rico.addPhraseId('filterNot',"not: $1");
Rico.addPhraseId('requestError',"The request for data returned an error:\n$1");
Rico.addPhraseId('keywordPrompt',"Enter keyword to search for (use * as a wildcard):");

// used in ricoLiveGridMenu.js

Rico.addPhraseId('gridmenuSortBy',"Sort by: $1");
Rico.addPhraseId('gridmenuSortAsc',"Ascending");
Rico.addPhraseId('gridmenuSortDesc',"Descending");
Rico.addPhraseId('gridmenuFilterBy',"Filter by: $1");
Rico.addPhraseId('gridmenuRefresh',"Refresh");
Rico.addPhraseId('gridmenuChgKeyword',"Change keyword...");
Rico.addPhraseId('gridmenuExcludeAlso',"Exclude this value also");
Rico.addPhraseId('gridmenuInclude',"Include only this value");
Rico.addPhraseId('gridmenuGreaterThan',"Greater than or equal to this value");
Rico.addPhraseId('gridmenuLessThan',"Less than or equal to this value");
Rico.addPhraseId('gridmenuContains',"Contains keyword...");
Rico.addPhraseId('gridmenuExclude',"Exclude this value");
Rico.addPhraseId('gridmenuRemoveFilter',"Remove filter");
Rico.addPhraseId('gridmenuRemoveAll',"Remove all filters");

Rico.addPhraseId('gridmenuExport',"Print/Export");
Rico.addPhraseId('gridmenuExportVis2Web',"Visible rows to web page");
Rico.addPhraseId('gridmenuExportAll2Web',"All rows to web page");
Rico.addPhraseId('gridmenuExportVis2SS',"Visible rows to spreadsheet");
Rico.addPhraseId('gridmenuExportAll2SS',"All rows to spreadsheet");

Rico.addPhraseId('gridmenuHideShow',"Hide/Show");
Rico.addPhraseId('gridmenuChooseCols',"Choose columns...");
Rico.addPhraseId('gridmenuHide',"Hide: $1");
Rico.addPhraseId('gridmenuShow',"Show: $1");
Rico.addPhraseId('gridmenuShowAll',"Show All");

// used in ricoLiveGridAjax.js

Rico.addPhraseId('sessionExpireMinutes',"minutes before your session expires");
Rico.addPhraseId('sessionExpired',"EXPIRED");
Rico.addPhraseId('requestTimedOut',"Request for data timed out!");
Rico.addPhraseId('waitForData',"Waiting for data...");
Rico.addPhraseId('httpError',"Received HTTP error: $1");
Rico.addPhraseId('invalidResponse',"Server returned an invalid response");

// used in ricoLiveGridCommon.js

Rico.addPhraseId('gridChooseCols',"Choose columns");
Rico.addPhraseId('exportComplete',"Exporting complete");
Rico.addPhraseId('exportInProgress',"Export in progress...");
Rico.addPhraseId('disableBlocker',"You need to disable your browser's pop-up blocker before exporting.");
Rico.addPhraseId('showFilterRow',"Show filter row");  // img alt text
Rico.addPhraseId('hideFilterRow',"Hide filter row");  // img alt text

// used in ricoLiveGridForms.js

Rico.addPhraseId('selectNone',"(none)");
Rico.addPhraseId('selectNewVal',"(new value)");
Rico.addPhraseId('record',"record");
Rico.addPhraseId('thisRecord',"this $1");
Rico.addPhraseId('confirmDelete',"Are you sure you want to delete $1?");
Rico.addPhraseId('deleting',"Deleting...");
Rico.addPhraseId('formPleaseEnter',"Please enter a value for $1");
Rico.addPhraseId('formInvalidFmt',"Invalid format for $1");
Rico.addPhraseId('formOutOfRange',"Value is out of range for $1");
Rico.addPhraseId('formNewValue',"new value:");
Rico.addPhraseId('saving',"Saving...");
Rico.addPhraseId('clear',"clear");
Rico.addPhraseId('close',"Close");
Rico.addPhraseId('saveRecord',"Save $1");
Rico.addPhraseId('cancel',"Cancel");
Rico.addPhraseId('editRecord',"Edit $1");
Rico.addPhraseId('deleteRecord',"Delete this $1");
Rico.addPhraseId('cloneRecord',"Clone $1");
Rico.addPhraseId('addRecord',"Add new $1");
Rico.addPhraseId('addedSuccessfully',"$1 added successfully");
Rico.addPhraseId('deletedSuccessfully',"$1 deleted successfully");
Rico.addPhraseId('updatedSuccessfully',"$1 updated successfully");

// used in ricoTree.js

Rico.addPhraseId('treeSave',"Save Selection");
Rico.addPhraseId('treeClear',"Clear All");

// used in ricoCalendar.js

Rico.addPhraseId('calToday',"Today is $1 $2 $3");  // $1=day, $2=monthabbr, $3=year, $4=month number
Rico.addPhraseId('calWeekHdg',"Wk");
Rico.addPhraseId('calYearRange',"Year ($1-$2)");
Rico.addPhraseId('calInvalidYear',"Invalid year");

// Date & number formats

Rico.thouSep=","
Rico.decPoint="."
Rico.dateFmt="mm/dd/yyyy"

Rico.monthNames=['January','February','March','April','May','June','July','August','September','October','November','December']
Rico.dayNames=['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday']
