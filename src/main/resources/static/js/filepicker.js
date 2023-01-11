
const client = filestack.init(fileStackApi);

let imgUrl = document.getElementById("imgurl");
let videoUrl = document.getElementById("videourl");

let imgPreview = document.getElementById("imgPreview");
let imgPreviewLabel = document.getElementById("imgPreviewLabel");
let imgButton = document.getElementById("imageButton");

let videoTag = document.getElementById("videoTag");
let videoPreview = document.getElementById("videoPreview");
let videoPreviewLabel = document.getElementById("videoPreviewLabel");
let videoButton = document.getElementById("videoButton");

let profilePicLink = document.getElementById("userProfilePic");
let shareButton = document.getElementById("shareButton");


imgButton.addEventListener("click",imgClickHandler);
videoButton.addEventListener("click", videoClickHandler);
profilePicLink.addEventListener("click", profileImgClickHandler);



function imgClickHandler(){
    console.log(imgPreview);
    const imgOptions = {
        accept: ["image/*"],
        onFileUploadFinished:file =>{
            imgUrl.value = file.url;
            imgPreview.src = file.url;
            imgPreviewLabel.hidden = false;
            imgPreview.hidden = false;
            console.log("FileUpload file url:"+file.url);
            console.log("FileUpload file size:"+file.size);

        }
    };

    client.picker(imgOptions).open();
}

function videoClickHandler(){
    console.log(videoPreview);
    const videoOptions = {
        accept: ["video/*"],
        disableTransformer: true,
        uploadInBackground: true,
        videoResolution: '1280x720',
        onFileUploadFinished:file =>{
            videoUrl.value = file.url;
            videoPreviewLabel.hidden = false;

            setTimeout(()=>{
                refreshDialer(videoUrl);
            },500);

            console.log("FileUpload file url:"+file.url);
            console.log("FileUpload file size:"+file.size);

        }
    };

    client.picker(videoOptions).open();
}

function profileImgClickHandler(){
    let imglink = "test";
    const imgOptions = {
        accept: ["image/*"],
        onFileUploadFinished:file =>{
            imglink = file.url;
            imglink = imglink.slice(8);
            console.log("Image Link: "+imglink);
            postData(imglink).then(data => console.log(data));
            console.log("FileUpload file url:"+file.url);
            console.log("FileUpload file size:"+file.size);

            setTimeout(()=>{
                location.reload();
            },500);

        }
    };


    client.picker(imgOptions).open();
    console.log("file picker ran");
    // postData(imglink).then(data => console.log(data));
}

async function postData(imgLink){
    let url = window.location.origin;

    let returnUrl = window.location.pathname;
    console.log("Return url: "+returnUrl);


    // await fetch(url,{
    //     method: 'GET',
    //     cache: "reload",
    //     body:{data:imgLink}
    // });


    url = url+"/users/imageUpload/"+imgLink+returnUrl;
    await fetch(url);
    console.log("Full url: "+url);
    console.log("img JS ran")
}

function refreshDialer(url){
    let html = `<video id="videoTag" width="270" height="160" autoplay="autoplay" controls="">
                                <source id="videoPreview" src=${url.value} type="video/mp4">
                            </video>`;
    document.getElementById("vidDiv").innerHTML = html;
}

