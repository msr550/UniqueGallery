### **Steps to integrate uniquegallery-lib**
uniquegallery-lib is a Library/Module used to integrate the customized gallery. As all of you know If we want to pick image or video from gallery, we need call Intent with ACTION_PICK. But here the problem is, in different devices it behaves differently and there is no proper solution to pick the image/video dynamically. To avoid the this problem I have created uniquegallery-lib. In this library we can simply differentiate selected file image or video along their parameters like path, date, resolution,duration.. etc.

## By Using below simple steps you can achieve it:

**Step1.** Download uniquegallery-lib from souce code and add as module dependency.

Here [How to add a module dependency to your project? ](http://stackoverflow.com/questions/18656023/androidstudio-module-dependencies-in-gradle)

**Step2.** Create class which extends Application in your code & write below code

    public class MyApplication extends Application {
      @Override
      public void onCreate() {
         super.onCreate();
         ApplicationLevel applicationLevel = new ApplicationLevel();
         applicationLevel.onCreate(this);
       }
    }

**Step3.** Now everything is setup. If you want to pick image/video/both from the out customized gallery follow below steps
* If you want pick image from gallery call intent by like this

        Intent intent = new Intent(this, AlbumActivity.class);
        intent.putExtra(Data.EXTRA_TYPE, Media.PHOTO);
        startActivityForResult(intent, REQUEST_CODE);

* If you want pick video from gallery call intent by like this

        Intent intent = new Intent(this, AlbumActivity.class);
        intent.putExtra(Data.EXTRA_TYPE, Media.VIDEO);
        startActivityForResult(intent, REQUEST_CODE);

* If you want pick image/video from gallery call intent by like this

        Intent intent = new Intent(this, PhotosVideosAlbumActivity.class);
        startActivityForResult(intent, REQUEST_CODE);


**Step4.** Now half of the task has been completed, by above code we are able to display images/video in custom gallery.Now we need to handle selected image/video.

**Code snipped for handle selected image/video**

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Object object = data.getSerializableExtra(getString(R.string.bundle_path));
                if (object instanceof PhotoEntry) {

                } else if (object instanceof VideoEntry) {
                    
                }
            }
        }
    }

**`PhotoEntry` class contains data related to photo**

**`VideoEntry` class contains data related to video**

_**## Screenshots for UniqueGallery library**_

[Gallery with only photos](https://drive.google.com/open?id=0Bz4bjZxHrdl8bUxNUWdXNkljdGc)

[Gallery with photos and videos](https://drive.google.com/open?id=0Bz4bjZxHrdl8RFg3dEIxU3RiZms)
