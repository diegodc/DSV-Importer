# DSV-importer

An easy way to read Delimiter-separated values files.

Example:

    TextReader reader = ReaderFactory.openReaderFromFile("file.txt");

    RecordFile<Foo> file = new RecordFile<>(reader, new FooConverter(), TokenDelimiter.COMMA);

    List<Foo> fooList = file.getRecords();