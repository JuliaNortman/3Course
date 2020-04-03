package com.knu.ynortman.idea;

import com.knu.ynortman.bits.BitArray;

import java.util.ArrayList;
import java.util.List;

public class Idea {
    private Key originalKey;
    private List<BitArray> encryptionKeys = new ArrayList<>();
    private List<BitArray> decryptionKeys = new ArrayList<>();

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

    public MessageBlock compute(MessageBlock inputBlock, List<BitArray> keys) {
        Round round = new Round();
        HalfRound halfRound = new HalfRound();

        MessageBlock resultBlock = inputBlock;
        for (int i = 0; i < 8;i++) {
            resultBlock = round.encrypt(resultBlock, keys.get(i * 6), keys.get(i * 6 + 1),
                    keys.get(i * 6 + 2), keys.get(i * 6 + 3), keys.get(i * 6 + 4),
                    keys.get(i * 6 + 5));
        }
        resultBlock = halfRound.encrypt(resultBlock, keys.get(48), keys.get(49), keys.get(50), keys.get(51));

        return resultBlock;
    }

    public MessageBlock encrypt(MessageBlock inputBlock) {
        return compute(inputBlock, encryptionKeys);
    }

    public MessageBlock decrypt(MessageBlock inputBlock) {
        return compute(inputBlock, decryptionKeys);
    }
}
