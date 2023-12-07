package com.emart.emart.utility;

import com.emart.emart.dtos.ResponseItemDto;
import com.emart.emart.dtos.ResponseListDto;
import com.emart.emart.dtos.ResponseMsgDto;

import java.util.List;

public class Utility {
    public static ResponseMsgDto convertToResponseMsgDto(String status, String description) {
        ResponseMsgDto responseMsgDTO = new ResponseMsgDto();
        responseMsgDTO.setStatus(status);
        responseMsgDTO.setDescription(description);
        return responseMsgDTO;
    }

    public static ResponseListDto convertToResponseListDto(String status, String description, List<?> responseList) {
        ResponseListDto responseDTO = new ResponseListDto();
        responseDTO.setStatus(status);
        responseDTO.setDescription(description);
        responseDTO.setResponseList(responseList);
        return responseDTO;
    }

    public static ResponseItemDto convertToResponseItemDto(String status, String description, Object object) {
        ResponseItemDto responseItemDTO = new ResponseItemDto();
        responseItemDTO.setStatus(status);
        responseItemDTO.setDescription(description);
        responseItemDTO.setObject(object);
        return responseItemDTO;
    }
}
