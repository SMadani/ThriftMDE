open System.IO

let isDirectory path = File.GetAttributes(path) = FileAttributes.Directory

let printFileLengths path =
    if (isDirectory path) then
        Directory.EnumerateFiles path
        |> Seq.sumBy (File.ReadAllLines >> Seq.length)
        |> printfn "%i"
    else
        printfn "%i" (Seq.length(File.ReadLines(path)))

[<EntryPoint>]
let main(args) =
    if (Array.length args <> 1) then
        printfn "The argument must be a path."
        -1
    else
        let path = Array.get args 0
        if (not <| (Directory.Exists(path) || File.Exists(path))) then
            printfn "Could not access path."
            -1
        else
            printFileLengths path
            0
    0