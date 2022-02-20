package com.company.dymrin25_2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.Socket;
import java.util.stream.Stream;

public class ClientHandlerTest {


    @ParameterizedTest
    @MethodSource("nullParametersForTest")
    public void testThrowRunTimeException_whenTheDataIsNull(ServerEngine serverEngine, Socket socket, int clientNumber) {
        Assertions.assertThrows(RuntimeException.class, () -> new ClientHandler(serverEngine, socket, clientNumber));

    }


    private static Stream<Arguments> nullParametersForTest() {
        return Stream.of(
                Arguments.of(null, new Socket(), 1),
                Arguments.of(new ServerEngine(), null, 1),
                Arguments.of(null, null, 1)

        );
    }
}
