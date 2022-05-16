package com.microservices.spring.cloud.users.ws.data;

import com.microservices.spring.cloud.users.ws.ui.model.response.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="albums-ws")
public interface AlbumsServiceClient {
    @GetMapping("/users/{id}/albumss")
    List<AlbumResponseModel> getAlbums(@PathVariable String id);
}
