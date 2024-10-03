package com.yicj.elasticsearch.es.index;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "contents_index", createIndex = false)
public class ContentsIndex implements Serializable {

    @Field(type= FieldType.Integer)
    private Integer id;

    @Field(type= FieldType.Keyword)
    private String title;

    @Field(type= FieldType.Keyword)
    private String intro;

    @Field(type= FieldType.Text)
    private String content;

    @Field(type= FieldType.Integer)
    private Integer createId;

    @Field(type= FieldType.Keyword)
    private String createName;

    @Field(type= FieldType.Date,format = DateFormat.date_hour_minute_second)
    private Date createTime;
}
