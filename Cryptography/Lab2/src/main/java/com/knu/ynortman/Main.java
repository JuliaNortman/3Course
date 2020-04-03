package com.knu.ynortman;

import com.knu.ynortman.idea.Idea;
import com.knu.ynortman.idea.MessageBlock;
import com.knu.ynortman.idea.Key;
import com.knu.ynortman.idea.StringToTextBlockConverter;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String stringToConvert = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum elementum " +
                "ultrices lacus. Nulla efficitur quam vel ex interdum luctus. Donec odio risus, congue eu sagittis ut," +
                " maximus at nibh.";

        Key key = new Key();
        key.setK(0, 0x050c);
        key.setK(1, 0x0a0b);
        key.setK(2, 0x00f0);
        key.setK(3, 0x0e00);
        key.setK(4, 0x0501);
        key.setK(5, 0x0103);
        key.setK(6, 0x010d);
        key.setK(7, 0x00cd);

        System.out.println("Input string: " + stringToConvert);
        System.out.println("Input key: " + key.toString());

        //input
        Idea idea = new Idea(key);

        System.out.println("Input blocks:");
        StringToTextBlockConverter stringToTextBlockConverter = new StringToTextBlockConverter();
        List<MessageBlock> blocks = stringToTextBlockConverter.convert(stringToConvert);
        for(MessageBlock block : blocks) {
            System.out.println(block.toHexString());
        }

        //encrypting
        System.out.println("Encrypted blocks:");
        List<MessageBlock> encryptedBlocks = new ArrayList<MessageBlock>();
        for(MessageBlock blockToEncrypt : blocks) {
            MessageBlock encryptedBlock = idea.encrypt(blockToEncrypt);

            encryptedBlocks.add(encryptedBlock);
            System.out.println(encryptedBlock.toHexString() + " ");
        }

        //decryptings
        StringBuilder stringOutput = new StringBuilder();
        System.out.println("Decrypted blocks:");
        for(MessageBlock blockToDecrypt : encryptedBlocks) {
            MessageBlock decryptedBlock = idea.decrypt(blockToDecrypt);

            System.out.println(decryptedBlock.toHexString());
            stringOutput.append(decryptedBlock.getBitArray().toASCII());
        }

        System.out.println("Decrypted string: " + stringOutput.toString());
    }
}
