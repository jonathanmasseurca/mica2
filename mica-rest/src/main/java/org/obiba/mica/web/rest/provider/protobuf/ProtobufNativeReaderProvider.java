/*******************************************************************************
 * Copyright 2008(c) The OBiBa Consortium. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.obiba.mica.web.rest.provider.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

@Component
@Provider
@Consumes("application/x-protobuf")
public class ProtobufNativeReaderProvider implements MessageBodyReader<Object> {

  @Inject
  protected ProtobufProviderHelper helper;

  private final int messageSizeLimit;

  @Autowired
  public ProtobufNativeReaderProvider(@Value("${org.obiba.opal.ws.messageSizeLimit}") int messageSizeLimit) {
    this.messageSizeLimit = messageSizeLimit;
  }

  @Override
  public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return Message.class.isAssignableFrom(type) || helper.isWrapped(type, genericType);
  }

  @Override
  public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
      MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
      throws IOException, WebApplicationException {
    Class<Message> messageType = helper.extractMessageType(type, genericType);
    ExtensionRegistry extensionRegistry = helper.extensions().forMessage(messageType);
    Builder builder = helper.builders().forMessage(messageType);
    if(helper.isWrapped(type, genericType)) {
      Collection<Message> msgs = new ArrayList<>();
      Builder b = builder.clone();
      while(b.mergeDelimitedFrom(entityStream, extensionRegistry)) {
        msgs.add(b.build());
        b = builder.clone();
      }
      return msgs;
    }
    CodedInputStream cis = CodedInputStream.newInstance(entityStream);
    cis.setSizeLimit(messageSizeLimit);
    return builder.mergeFrom(cis, extensionRegistry).build();
  }
}
