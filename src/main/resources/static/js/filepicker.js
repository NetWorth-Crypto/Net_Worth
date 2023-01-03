
const client = filestack.init(fileStackApi);

let imgUrl = document.getElementById("imgurl");
let videoUrl = document.getElementById("videourl");

let imgButton = document.getElementById("imageButton");
let videoButton = document.getElementById("videoButton");



imgButton.addEventListener("click",imgClickHandler);
videoButton.addEventListener("click", videoClickHandler);



function imgClickHandler(){
    const imgOptions = {
        accept: ["image/*"],
        onFileUploadFinished:file =>{
            imgUrl.value = file.url;
            console.log("FileUpload file url:"+file.url);
            console.log("FileUpload file size:"+file.size);

        }
    };

    client.picker(imgOptions).open();
}

function videoClickHandler(){
    const videoOptions = {
        accept: ["video/*"],
        disableTransformer: true,
        uploadInBackground: true,
        videoResolution: '1280x720',
        onFileUploadFinished:file =>{
            videoUrl.value = file.url;
            console.log("FileUpload file url:"+file.url);
            console.log("FileUpload file size:"+file.size);

        }
    };

    client.picker(videoOptions).open();
}



