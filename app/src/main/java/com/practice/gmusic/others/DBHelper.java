package com.practice.gmusic.others;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, Const.dbName, null, Const.dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create Table

        String createUser = "CREATE TABLE User (uid INTEGER PRIMARY KEY AUTOINCREMENT,mail TEXT," +
                "pass TEXT, name TEXT)";

        String createTrack = "CREATE TABLE Track(tid INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT," +
                "author TEXT, url TEXT, img TEXT)";

        String createCategory = "CREATE TABLE Category(cid INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT," +
                "tid INTEGER," +
                "FOREIGN KEY (tid) REFERENCES Track(tid))";

        String createPlaylist = "CREATE TABLE Playlist(pid INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT," +
                "uid INTEGER," +
                "FOREIGN KEY (uid) REFERENCES User(uid))";

        String createDetail = "CREATE TABLE Detail(did INTEGER PRIMARY KEY AUTOINCREMENT, pid INTEGER," +
                "tid INTEGER," +
                "FOREIGN KEY (tid) REFERENCES Track(tid)," +
                "FOREIGN KEY (pid) REFERENCES Playlist(pid))";

        db.execSQL(createUser);
        db.execSQL(createTrack);
        db.execSQL(createCategory);
        db.execSQL(createPlaylist);
        db.execSQL(createDetail);


        //Init Table

        String initUser = "INSERT INTO User VALUES (NULL,'alexziglar@mail.com','123','Alex Ziglar')," +
                "(NULL,'beckyyuhiko@mail.com','123','Becky Yuhiko'),(NULL,'cypherxico@mail.com','123','Cypher Xico')";

        String initTrack = "INSERT INTO Track VALUES " +
                "(NULL,'Invincible','Deaf Kev','https://ncs.io/track/download/817ec1ef-bac0-4c8a-9671-857fdd13cfa9','https://i1.sndcdn.com/artworks-000130095075-mpzysz-t500x500.jpg')," +
                "(NULL,'Summer Time','Erik Lund','https://tainhacmienphi.mobi/download-music/15040','https://i.ytimg.com/vi/9c2rW2Jd2DM/maxresdefault.jpg')," +
                "(NULL,'Happy Life','Fredji','https://tainhac365.org/download-music/92555','https://i.ytimg.com/vi/15o91goZSJ0/maxresdefault.jpg')," +
                "(NULL,'Xenogenesis','The Fat Rat','https://tainhacmienphi.mobi/download-music/8920','https://i1.sndcdn.com/artworks-AwiSKoqcK53A4hvF-6ITong-t500x500.jpg')," +
                "(NULL,'Untitled','Lofi Hour','https://cdn.pixabay.com/download/audio/2022/10/22/audio_e037c90cbb.mp3?filename=untitled-123636.mp3','https://mir-s3-cdn-cf.behance.net/project_modules/2800_opt_1/420f2651337667.58eb38b95227a.jpg')," +
                "(NULL,'Street Food','Fas Sound','https://cdn.pixabay.com/download/audio/2022/05/27/audio_e6db7e7e8e.mp3?filename=street-food-112193.mp3','https://images-platform.99static.com//X43Bwata7FhvxA45QDU-LoOAXBk=/311x129:1174x992/fit-in/500x500/99designs-contests-attachments/81/81603/attachment_81603309')," +
                "(NULL,'Sweet Love','Day Fox','https://cdn.pixabay.com/download/audio/2022/10/02/audio_8f97a56643.mp3?filename=sweet-love-121561.mp3','https://previews.123rf.com/images/varana/varana1802/varana180200085/96072435-sweet-love-text-isolated-on-background-hand-drawn-lettering-love-as-logo-badge-icon-patch-sticker-te.jpg')," +
                "(NULL,'Chilling Ego','Lesfm','https://cdn.pixabay.com/download/audio/2021/09/27/audio_91dc5d749a.mp3?filename=chilling-ego-8753.mp3','https://source.boomplaymusic.com/group10/M00/05/22/90a8dbe7933c4ca2a9c856eb737684ea_464_464.jpg')," +
                "(NULL,'906090','Tóc Tiên, Mew Amazing','https://firebasestorage.googleapis.com/v0/b/musicplayerapp-e9540.appspot.com/o/906090-TocTienMewAmazing-8078581.mp3?alt=media&token=27eafc71-5b21-44e7-a85c-4f6056c1fa6d','https://photo-resize-zmp3.zmdcdn.me/w240_r1x1_jpeg/cover/1/e/e/1/1ee19270545210d8de618e94ee6f1f14.jpg')," +
                "(NULL,'Bật Nhạc Lên','Hieuthuhai, Harmonie','https://stream.nixcdn.com/NhacCuaTui1001/BatNhacLen1-HIEUTHUHAIHarmonie-6351919.mp3?st=k9z2D2FogW8EKmczE1wBWQ&e=1670686134','https://i.scdn.co/image/ab67616d0000b273b4f1e181cea447b60048be00')," +
                "(NULL,'Em Là','Mono, Onion','https://tainhacmienphi.biz/download-music/459610','https://images.genius.com/df4f44ceab6fbc6cc2f73a71f0711af6.600x600x1.jpg')," +
                "(NULL,'Qua Khung Cửa Sổ','Chillies','https://data3.chiasenhac.com/downloads/2130/1/2129844-9013b480/32/Qua%20Khung%20Cua%20So%20-%20Chillies.m4a','https://data.chiasenhac.com/data/cover/131/130653.jpg')," +
                "(NULL,'Double Take','Dhruv','https://tainhacmienphi.mobi/download-music/141089','https://i.ytimg.com/vi/uQiF1yOnzDg/maxresdefault.jpg')," +
                "(NULL,'Symphony','Clean Bandit, Zara Larsson','https://firebasestorage.googleapis.com/v0/b/musicplayerapp-e9540.appspot.com/o/Symphony-CleanBanditZaraLarsson-4822950.mp3?alt=media&token=c706ebb2-a84f-41a5-885f-b8330fe57424','https://i.scdn.co/image/ab67616d0000b27375a7e568ba342c3bf17ecd57')," +
                "(NULL,'Unholy','Sam Smith, Kim Petras','https://firebasestorage.googleapis.com/v0/b/musicplayerapp-e9540.appspot.com/o/Unholy-SamSmithKimPetras-7966046.mp3?alt=media&token=b08de34d-1017-42bc-9cbb-d38cc9609955','https://i.ytimg.com/vi/8VKD-IlvibI/maxresdefault.jpg')," +
                "(NULL,'Thats What I Want','Lil Nas X','https://firebasestorage.googleapis.com/v0/b/musicplayerapp-e9540.appspot.com/o/That_s%20What%20I%20Want%20-%20Lil%20Nas%20X.mp3?alt=media&token=2f10908d-1554-40fd-9d3f-ab7cc417df59','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRJUmVzsZe1e0g7FaEoBI_9B86Z-NFaBszbHg&usqp=CAU')," +
                "(NULL,'See Tình','Hoàng Thùy Linh','https://firebasestorage.googleapis.com/v0/b/musicplayerapp-e9540.appspot.com/o/See%20Tinh%20-%20Hoang%20Thuy%20Linh.mp3?alt=media&token=a32eec91-9f95-4048-8508-22c0bc671843','https://image.thanhnien.vn/w1024/Uploaded/2022/lxwpcqjwp/2022_03_02/anh-2-7429.jpg')," +
                "(NULL,'Itaewon Class OST','Gaho','https://firebasestorage.googleapis.com/v0/b/musicplayerapp-e9540.appspot.com/o/BeginningItaewonClassOst-Gaho-6216170.mp3?alt=media&token=1c4c18d9-02c2-47f1-94c1-d75c9eda9091','https://avatar-ex-swe.nixcdn.com/song/2020/02/04/2/a/4/0/1580799231699_640.jpg')," +
                "(NULL,'Levels','Avicii','https://firebasestorage.googleapis.com/v0/b/musicplayerapp-e9540.appspot.com/o/Levels%20-%20Avicii.mp3?alt=media&token=f07553bd-72f5-4299-b8f1-a34c6339f0a7','https://upload.wikimedia.org/wikipedia/vi/2/2c/Levelssong.jpg')," +
                "(NULL,'Legends Never Die','Against The Current','https://firebasestorage.googleapis.com/v0/b/musicplayerapp-e9540.appspot.com/o/Legends%20Never%20Die%20-%20Against%20The%20Current.mp3?alt=media&token=a7651e49-c192-45f2-93f1-e5fc78fd7f03','https://i1.sndcdn.com/artworks-4VMsuOwfAof3MwxM-b8Hymw-t500x500.jpg')";

        String initCategory = "INSERT INTO Category VALUES " +
                "(NULL,'Instrumental',1),(NULL,'Instrumental',2),(NULL,'Instrumental',3),(NULL,'Instrumental',4)," +
                "(NULL,'Lofi Chill',5),(NULL,'Lofi Chill',6),(NULL,'Lofi Chill',7),(NULL,'Lofi Chill',8)," +
                "(NULL,'Asia',9),(NULL,'Asia',10),(NULL,'Asia',11),(NULL,'Asia',12)," +
                "(NULL,'US UK',13),(NULL,'US UK',14),(NULL,'US UK',15),(NULL,'US UK',16),(NULL,'Việt Nam',17)," +
                "(NULL,'Trending',3),(NULL,'Trending',5),(NULL,'Trending',7),(NULL,'Trending',9),(NULL,'Trending',11),(NULL,'Trending',13)";

        String initPlaylist = "INSERT INTO Playlist VALUES (NULL,'Demo',1),(NULL,'Test',1),(NULL,'Test',2)," +
                "(NULL,'Test',3)";

        String initDetail = "INSERT INTO Detail VALUES (NULL,1,0),(NULL,1,5),(NULL,1,10),(NULL,2,2),(NULL,2,4),(NULL,2,6)," +
                "(NULL,3,3),(NULL,3,5),(NULL,3,7),(NULL,4,16),(NULL,4,17),(NULL,4,18)";

        db.execSQL(initUser);
        db.execSQL(initTrack);
        db.execSQL(initCategory);
        db.execSQL(initPlaylist);
        db.execSQL(initDetail);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropUser = "DROP TABLE IF EXISTS User";
        String dropTrack = "DROP TABLE IF EXISTS Track";
        String dropCategory = "DROP TABLE IF EXISTS Category";
        String dropPlaylist = "DROP TABLE IF EXISTS Playlist";
        String dropDetail = "DROP TABLE IF EXISTS Detail";

        db.execSQL(dropUser);
        db.execSQL(dropTrack);
        db.execSQL(dropCategory);
        db.execSQL(dropPlaylist);
        db.execSQL(dropDetail);

        onCreate(db);
    }
}
