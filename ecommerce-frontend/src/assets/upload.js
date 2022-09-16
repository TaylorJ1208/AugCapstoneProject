const url = "https://api.cloudinary.com/v1_1/demo/image/upload";
const form = document.getElementById("imageuploadform");

console.log("In the 'upload.js' script");

function uploadImage(){
  
console.log("got js");
  const files = document.querySelector("[type=file]").files;
  const formData = new FormData();

  for(let f of files) {
    let file = f;
    formData.append("file", file);
    formData.append("ml_default", "ml_default");
  
    fetch(url, {
      method: "POST",
      body: formData
    })
      .then((response) => {
        return response.text();
      })
      .then((data) => {
        document.getElementById("data").innerHTML += data;
      });
  }
}



let myWidget = cloudinary.createUploadWidget({
    cloudName: 'ajaybajwa', 
    uploadPreset: 'zfppobuq'}, (error, result) => { 
      if (!error && result && result.event === "success") { 
        console.log('Done! Here is the image info: ', result.info);
        document.getElementById("imageuri").select();
        document.getElementById("imageuri").value = result.info.url;
        document.getElementById("productimage").src = result.info.url;
        document.getElementById("productimage").hidden = false;
        document.getElementById("upload_widget").textContent = "Change Image";
        console.log("Image URL is "+result.info.url); 
      }
    }
  )
  
  function openWidget(){
      myWidget.open();
    }