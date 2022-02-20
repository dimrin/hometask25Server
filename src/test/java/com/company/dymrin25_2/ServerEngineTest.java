package com.company.dymrin25_2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.net.Socket;
import java.util.stream.Stream;

public class ServerEngineTest {

    @ParameterizedTest
    @MethodSource("nullParametersForTest")
    public void testThrowNullPointerException_whenTheDataIsNull_forRegistr(String[] strings, ClientHandler clientHandler) {
        Assertions.assertThrows(NullPointerException.class, () -> new ServerEngine().register(strings, clientHandler));
    }

    @ParameterizedTest
    @NullSource
    public void testThrowRunTimeException_whenStringArrayIsNull_forRegistr(String[] strings) {
        Assertions.assertThrows(RuntimeException.class, () -> new ServerEngine().register(strings, new ClientHandler(new ServerEngine(), new Socket(), 1)));
    }

    @ParameterizedTest
    @MethodSource("nullParametersForTest")
    public void testThrowNullPointerException_whenTheDataIsNull_forDelete(String[] strings, ClientHandler clientHandler) {
        Assertions.assertThrows(NullPointerException.class, () -> new ServerEngine().deleteUser(strings, clientHandler));

    }

    @ParameterizedTest
    @NullSource
    public void testThrowRunTimeException_whenStringArrayIsNull_forDelete(String[] strings) {
        Assertions.assertThrows(RuntimeException.class, () -> new ServerEngine().deleteUser(strings, new ClientHandler(new ServerEngine(), new Socket(), 1)));
    }

    @ParameterizedTest
    @NullSource
    public void testThrowNullPointerException_whenTheDataIsNull_forBroadcastMessage(String message) {
        Assertions.assertThrows(NullPointerException.class, () -> new ServerEngine().broadcastMessage(message));

    }

    private static Stream<Arguments> nullParametersForTest() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new String[3], null)
        );
    }
}
