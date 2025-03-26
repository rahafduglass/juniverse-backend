package juniverse.domain.mappers;


import juniverse.application.dtos.news.NewsRequest;
import juniverse.domain.models.NewsModel;
import juniverse.persistance.entities.NewsEntity;
import juniverse.persistance.entities.SysUserEntity;
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

}
