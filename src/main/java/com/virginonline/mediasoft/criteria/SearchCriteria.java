package com.virginonline.mediasoft.criteria;

import com.virginonline.mediasoft.criteria.field.Field;
import java.util.List;
import lombok.Data;

@Data
public class SearchCriteria {
  List<Field> fields;
}
