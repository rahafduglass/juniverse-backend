package juniverse.domain.mappers.news;


import juniverse.application.dtos.news.NewsRequest;
import juniverse.application.dtos.news.NewsResponse;
import juniverse.domain.models.news.NewsModel;
import juniverse.persistance.entities.news.NewsEntity;
import juniverse.persistance.entities.user.SysUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    NewsModel requestToModel(NewsRequest newsRequest);

    @Mapping(source="authorId", target="author")
    @Mapping(source="updatedById",target="updatedBy")
    NewsEntity modelToEntity(NewsModel newsModel);

    default SysUserEntity mapSysUserIdToEntity(Long sysUserId) {
        if (sysUserId == null) {
            return null;
        }

        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId(sysUserId);

        return sysUserEntity;
    }

    NewsModel entityToModel(NewsEntity newsEntity);

    NewsResponse modelToResponse(NewsModel newsModel);
}
