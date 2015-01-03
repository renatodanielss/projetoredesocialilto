<script type="text/javascript" src="/<%= mytext.display("adminpath") %>/swfupload/swfupload.js"></script> 
<script type="text/javascript">

var swfu = false;

function queueMultiFileUpload(file) {
	var queue = document.getElementById("multifileuploadqueue");
	if (queue) {
		if (file) {
			var span = document.createElement("SPAN");
			span.innerHTML = file.name+"<br>";
			queue.appendChild(span);
		} else {
			queue.innerHTML = "";
			try {
				var stats = swfu.getStats();
				for (var i=0; i<stats.files_queued; i++) {
					var file = swfu.getFile(i);
					var span = document.createElement("SPAN");
					span.innerHTML = file.name+"<br>";
					queue.appendChild(span);
				}
			} catch(e) {
			}
		}
		if (mySlideTabs) mySlideTabs.tabs_resize();
	}
}

function clearQueueMultiFileUpload() {
	var queue = document.getElementById("multifileuploadqueue");
	if (queue) {
		queue.innerHTML = "";
		if (mySlideTabs) mySlideTabs.tabs_resize();
	}
}

function showMultiFileUploadProgress(file, bytesLoaded, bytesTotal) {
	var progress = document.getElementById("multifileuploadprogress");
	if (progress) {
		var stats = swfu.getStats();
		var totalfiles = 0 + stats.files_queued + stats.successful_uploads + stats.upload_errors + stats.upload_cancelled + stats.queue_errors;
		var currentfile = 1 + totalfiles - stats.files_queued;
		if (currentfile > totalfiles) currentfile = totalfiles;
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
		progress.innerHTML = '<br><%= mytext.display("wait.file.uploading") %> ' + currentfile + '/' + totalfiles + ': ' + file.name + ' - ' + bytesLoaded + '/' + bytesTotal + ' - ' + percent + '% ' + '<br>';
		if (mySlideTabs) mySlideTabs.tabs_resize();
	}
}

function queueMultiFileUploadError(file, errorCode, message)  {
	switch (errorCode) {
	case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
		alert("You have attempted to queue too many files.\n" + (message === 0 ? "You have reached the upload limit." : "You may select " + (message > 1 ? "up to " + message + " files." : "one file.")));
		return;
	case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
		alert("The file you selected is too big.");
		this.debug("Error Code: File too big, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
		return;
	case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
		alert("The file you selected is empty.  Please select another file.");
		this.debug("Error Code: Zero byte file, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
		return;
	case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
		alert("The file you choose is not an allowed file type.");
		this.debug("Error Code: Invalid File Type, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
		return;
	default:
		alert("An error occurred in the upload. Try again later.");
		this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
		return;
	}
}

function doMultiFileUpload(form) {
	swfu.addPostParam("username", "<%= mysession.get("username") %>");
	swfu.addPostParam("password", "<%= mysession.get("password") %>");
	var stats = swfu.getStats();
	if (stats.files_queued > 0) {
		var title = "";
		var server_filename = "";
		for (var i=0; i<form.elements.length; i++) {
			var input = form.elements[i];
			if (input.name) {
				if (input.name == "title") {
					title = input.value;
				} else if (input.name == "server_filename") {
					server_filename = input.value;
				} else if (input.type == "hidden") {
					swfu.addPostParam(input.name, input.value);
				} else if (input.type == "text") {
					swfu.addPostParam(input.name, input.value);
				} else if (input.type == "textarea") {
					swfu.addPostParam(input.name, input.value);
				} else if (input.type == "checkbox") {
					if (input.checked) swfu.addPostParam(input.name, input.value);
				} else if (input.type == "radio") {
					swfu.addPostParam(input.name, input.value);
				} else if (input.type == "select-one") {
					swfu.addPostParam(input.name, input.value);
				} else if (input.type == "select-multiple") {
					for (var j=0; j<input.options.length; j++) {
						var option = input.options[j];
						if (option.selected) {
							swfu.addPostParam(input.name, input.value);
						}
					}
				} else if (input.type == "file") {
					// ignore
				}
			}
		}
		if (title != "") title = (title + " ").replace("  $", "");
		for (var i=0; i<stats.files_queued; i++) {
			var file = swfu.getFile(i);
			swfu.addFileParam(file.id, "title", title + file.name);
			if (server_filename != "") {
				swfu.addFileParam(file.id, "server_filename", server_filename + file.name);
			}
		}
		startMultiFileUpload();
		return true;
	} else {
		return false;
	}
}

function startMultiFileUpload(uploadedfile, serverData) {
	var stats = swfu.getStats();
	if (stats.files_queued > 0) {
		if (! stats.in_progress) swfu.startUpload();
	} else if (swfu.customSettings.redirect) {
		if (typeof(swfu.customSettings.redirect) == "function") {
			swfu.customSettings.redirect();
		} else {
			document.location.href = swfu.customSettings.redirect;
		}
	} else {
		document.location.href = "<%= Common.redirectURL(db, myconfig, myrequest.getParameter("redirect"), "") %>" || ("/<%= mytext.display("adminpath") %>/content/index.jsp?"+Math.random(1));
	}
}

function displayMultiFileUploadError(uploadedfile, serverData) {
	if (uploadedfile && serverData && (serverData != "") && (serverData != " ")) {
		var myerror = document.getElementById("multifileuploaderror");
		if (myerror) {
			myerrors.innerHTML = "" + uploadedfile.name + " - " + serverData + "<br>";
		}
		var myerrors = document.getElementById("multifileuploaderrors");
		if (myerrors) {
			myerrors.innerHTML += "" + uploadedfile.name + " - " + serverData + "<br>";
		}
		if (mySlideTabs) mySlideTabs.tabs_resize();
	}
}

function displayMultiFileUploadSuccess(uploadedfile, serverData) {
	if (uploadedfile && serverData && (serverData != " ")) {
		var myerrors = document.getElementById("multifileuploadsuccess");
		if (myerrors) {
			myerrors.innerHTML += '<br><font color="red"><%= mytext.display("wait.file.error") %> ' + uploadedfile.name + ' - ' + serverData + '</font>';
		}
	} else if (uploadedfile && serverData) {
		var myerrors = document.getElementById("multifileuploadsuccess");
		if (myerrors) {
			myerrors.innerHTML += '<br><%= mytext.display("wait.file.uploaded") %> ' + uploadedfile.name;
		}
		if (mySlideTabs) mySlideTabs.tabs_resize();
	}
}

</script>
