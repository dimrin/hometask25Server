package com.company.dymrin25_2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.stream.Stream;

public class ClientHandlerMessagerTest {


    @ParameterizedTest
    @MethodSource("nullStreamsForTestSend")
    public void testThrowNullPointerException_whenTheStreamsIsNull_forSend(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        Assertions.assertThrows(NullPointerException.class, () -> new ClientHandlerMessager(dataInputStream, dataOutputStream).sendOutboundMessage(""));

    }

    @ParameterizedTest
    @MethodSource("nullStreamsForTestListen")
    public void test1(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        Assertions.assertThrows(NullPointerException.class, () -> new ClientHandlerMessager(dataInputStream, dataOutputStream).listenInboundMessage());

    }


    private static Stream<Arguments> nullStreamsForTestSend() {
        return Stream.of(
                Arguments.of(new DataInputStream(new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                }), null),
                Arguments.of(null, null)
        );
    }

    private static Stream<Arguments> nullStreamsForTestListen() {
        return Stream.of(
                Arguments.of(null, new DataOutputStream(new OutputStream() {
                    @Override
                    public void write(int b) throws IOException {

                    }
                })),
                Arguments.of(null, null)
        );
    }


}
