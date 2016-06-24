var openFile = function(e, nameImage) {


    var input = e.target;

    var reader = new FileReader();
    reader.onload = function(){
      var dataURL = reader.result;
      var preview_photo = document.getElementById(nameImage);
      preview_photo.src = dataURL;
      img.src = dataURL;
    };
    reader.readAsDataURL(input.files[0]);
  }

  function redimensionar(nameImage, nameCanvas, maxWidth, maxHeight){
    var canvas = document.getElementById(nameCanvas);
    var ctx = canvas.getContext('2d');
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    var img = document.getElementById(nameImage);

    if (img.height > maxHeight || img.width > maxWidth){
      if (img.height >= img.width){
        resizeCanvas(ctx, canvas, getNewWidth(img.height, img.width, maxHeight), maxHeight);
        ctx.drawImage(document.getElementById(nameImage),0,0,getNewWidth(img.height, img.width, maxHeight), maxHeight);
      }
      else{
        resizeCanvas(ctx, canvas, maxWidth, getNewHeight(img.height, img.width, maxWidth));
        ctx.drawImage(document.getElementById(nameImage),0,0,maxWidth, getNewHeight(img.height, img.width, maxWidth));
      }
    }
    else{
      resizeCanvas(ctx, canvas, img.width, img.height);
      ctx.drawImage(document.getElementById(nameImage),0,0, img.width, img.height);
    }
  }

  function resizeCanvas(ctx, canvasRef, w, h) {
    var inMemCanvas = document.createElement('canvas');
    var inMemCtx = inMemCanvas.getContext('2d');

    inMemCanvas.width = canvasRef.width;
    inMemCanvas.height = canvasRef.height;
    inMemCtx.drawImage(canvasRef, 0, 0);
    canvasRef.width = w;
    canvasRef.height = h;
    ctx.drawImage(inMemCanvas, 0, 0);
  }

  function getNewHeight(height, width, newWidth){
    return Math.floor(height / width * newWidth);
  }

  function getNewWidth(height, width, newHeight){
    return Math.floor(width / height * newHeight);
  }

function replaceNumber(e, fieldName){
  var field = document.getElementById(fieldName).value;
  if (!(field.charCodeAt(field.length-1) >= 48 && field.charCodeAt(field.length-1) <= 57)){
    document.getElementById(fieldName).value = document.getElementById(fieldName).value.substring(0, field.length - 1);
  }
}