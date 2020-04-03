package com.knu.ynortman.idea;

import com.knu.ynortman.bits.BitArray;

import java.util.ArrayList;
import java.util.List;

public class Idea {
    private Key originalKey;
    private List<BitArray> encryptionKeys = new ArrayList<>();
    private List<BitArray> decryptionKeys = new ArrayList<>();

    List<TextBlock> encryptedBlocks = new ArrayList<>();
    StringBuilder stringOutput = new StringBuilder();

    public Idea(Key originalKey) {
        this.originalKey = originalKey;

        generateAllSubkeys();
    }

    private void generateAllSubkeys() {
        Key nextKey = originalKey;

        for(int i = 0;i < 6;i++) {
            encryptionKeys.addAll(nextKey.getSubkeys());
            nextKey = nextKey.generateNextKey();
        }
        encryptionKeys.addAll(nextKey.getHalfOfSubkeys());

        //generate decrypt keys
        decryptionKeys.add(encryptionKeys.get(48).invert());
        decryptionKeys.add(encryptionKeys.get(49).twosComplement());
        decryptionKeys.add(encryptionKeys.get(50).twosComplement());
        decryptionKeys.add(encryptionKeys.get(51).invert());

        for(int i = 7;i >= 0; i--) {
            decryptionKeys.add(encryptionKeys.get(i * 6 + 4));
            decryptionKeys.add(encryptionKeys.get(i * 6 + 5));

            decryptionKeys.add(encryptionKeys.get(i * 6 + 0).invert());
            if (i == 0) {
                decryptionKeys.add(encryptionKeys.get(i * 6 + 1).twosComplement());
                decryptionKeys.add(encryptionKeys.get(i * 6 + 2).twosComplement());
            } else {
                decryptionKeys.add(encryptionKeys.get(i * 6 + 2).twosComplement());
                decryptionKeys.add(encryptionKeys.get(i * 6 + 1).twosComplement());
            }

            decryptionKeys.add(encryptionKeys.get(i * 6 + 3).invert());
        }
    }

    private TextBlock compute(TextBlock inputBlock, List<BitArray> keys) {
        Round round = new Round();
        HalfRound halfRound = new HalfRound();

        TextBlock resultBlock = inputBlock;
        for (int i = 0; i < 8;i++) {
            resultBlock = round.encrypt(resultBlock, keys.get(i * 6), keys.get(i * 6 + 1),
                    keys.get(i * 6 + 2), keys.get(i * 6 + 3), keys.get(i * 6 + 4),
                    keys.get(i * 6 + 5));
        }
        resultBlock = halfRound.encrypt(resultBlock, keys.get(48), keys.get(49), keys.get(50), keys.get(51));

        return resultBlock;
    }

    private TextBlock encryptTextBlock(TextBlock inputBlock) {
        return compute(inputBlock, encryptionKeys);
    }

    private TextBlock decryptTextBlock(TextBlock inputBlock) {
        return compute(inputBlock, decryptionKeys);
    }

    public List<TextBlock> encrypt(String message) {
        List<TextBlock> blocks =  new StringToTextBlockConverter().convert(message);

        //encrypting
        List<TextBlock> encryptedBlocks = new ArrayList<>();
        for(TextBlock blockToEncrypt : blocks) {
            encryptedBlocks.add(encryptTextBlock(blockToEncrypt));
        }
        return encryptedBlocks;
    }

    public String decrypt(List<TextBlock> encryptedBlocks) {
        StringBuilder stringOutput = new StringBuilder();
        for(TextBlock blockToDecrypt : encryptedBlocks) {
            TextBlock decryptedBlock = decryptTextBlock(blockToDecrypt);
            stringOutput.append(decryptedBlock.getBitArray().toASCII());
        }
        return stringOutput.toString();
    }
}
