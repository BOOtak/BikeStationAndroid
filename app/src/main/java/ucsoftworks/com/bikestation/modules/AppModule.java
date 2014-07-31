package ucsoftworks.com.bikestation.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ucsoftworks.com.bikestation.web_service.BikeApi;
import ucsoftworks.com.bikestation.web_service.BikeServiceApi;

/**
 * Created by Pasenchuk Victor on 28.07.14 in IntelliJ Idea
 */

@Module(
        complete = true,
        library = true)
public class AppModule {
    @Provides
    @Singleton
    BikeServiceApi provideBikeService() {
        return new BikeApi();
    }

}