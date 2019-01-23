#include <jni.h>
#include <string>
#include "MovieController.cpp"

extern "C" JNIEXPORT jstring
JNICALL
Java_movielist_example_com_movielist_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C" JNIEXPORT jobjectArray
JNICALL
Java_movielist_example_com_movielist_model_MovieController_getMovies(
        JNIEnv *env,
        jobject /* this */) {

    jclass cls = env->FindClass("movielist/example/com/movielist/model/Movie");
    jmethodID constructortorID = env->GetMethodID(cls, "<init>", "()V");
    MovieController *movieController = new MovieController();
    std::vector<Movie*> movies = movieController->getMovies();
    jobjectArray jMoviesArray = env->NewObjectArray(movies.size(), cls, NULL);


    for (size_t i = 0; i < movies.size(); i++) {
        jobject jMovie = env->NewObject(cls, constructortorID);

        jfieldID nameID = env->GetFieldID(cls, "name", "Ljava/lang/String;");
        env->SetObjectField(jMovie, nameID, env->NewStringUTF(movies[i]->name.c_str()));

        jfieldID lastUpdatedID = env->GetFieldID(cls, "lastUpdated", "I");
        env->SetIntField(jMovie, lastUpdatedID, (jint)movies[i]->lastUpdated);
        env->SetObjectArrayElement(jMoviesArray, i, jMovie);
    }
    return jMoviesArray;
}


extern "C" JNIEXPORT jobject
JNICALL
Java_movielist_example_com_movielist_model_MovieController_getMovieDetails(
        JNIEnv *env,
        jobject thisClass,
        jstring movieNameString) {

        MovieController *movieController = new MovieController();

        const char *movieNameChars = env->GetStringUTFChars(movieNameString, 0);
        MovieDetail *movieDetail = movieController->getMovieDetail(movieNameChars);

        jclass cls = env->FindClass("movielist/example/com/movielist/model/MovieDetail");
        jmethodID constructorID = env->GetMethodID(cls, "<init>", "()V");

        jobject jMovieDetail = env->NewObject(cls, constructorID);

        jfieldID nameID = env->GetFieldID(cls, "name", "Ljava/lang/String;");
        env->SetObjectField(jMovieDetail, nameID, env->NewStringUTF(movieDetail->name.c_str()));

        jfieldID scoreID = env->GetFieldID(cls, "score", "F");
        env->SetFloatField(jMovieDetail, scoreID, (jfloat)movieDetail->score);

        jfieldID descriptionID = env->GetFieldID(cls, "description", "Ljava/lang/String;");
        env->SetObjectField(jMovieDetail, descriptionID, env->NewStringUTF(movieDetail->description.c_str()));


        return jMovieDetail;
}

extern "C" JNIEXPORT jobject
JNICALL
Java_movielist_example_com_movielist_model_MovieController_getMovieActors(
        JNIEnv *env,
        jobject thisClass,
        jstring movieNameString) {

    MovieController *movieController = new MovieController();

    const char *movieNameChars = env->GetStringUTFChars(movieNameString, 0);
    MovieDetail *movieDetail = movieController->getMovieDetail(movieNameChars);

    jclass cls = env->FindClass("movielist/example/com/movielist/model/Actor");
    jmethodID constructorID = env->GetMethodID(cls, "<init>", "()V");

    jobjectArray jActorsArray = env->NewObjectArray(movieDetail->actors.size(), cls, NULL);

    for (size_t i = 0; i < movieDetail->actors.size(); i++) {
        jobject jActor = env->NewObject(cls, constructorID);

        jfieldID nameID = env->GetFieldID(cls, "name", "Ljava/lang/String;");
        env->SetObjectField(jActor, nameID, env->NewStringUTF(movieDetail->actors[i].name.c_str()));

        jfieldID ageID = env->GetFieldID(cls, "age", "I");
        env->SetIntField(jActor, ageID, (jint)movieDetail->actors[i].age);

        jfieldID imageUrlID = env->GetFieldID(cls, "imageUrl", "Ljava/lang/String;");
        env->SetObjectField(jActor, imageUrlID, env->NewStringUTF(movieDetail->actors[i].imageUrl.c_str()));

        env->SetObjectArrayElement(jActorsArray, i, jActor);
    }

    return jActorsArray;
}