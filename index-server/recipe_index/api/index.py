import flask
import math
import recipe_index
import re
# import cProfile, pstats, io
# from pstats import SortKey
# pr = cProfile.Profile()

@recipe_index.app.route('/api/v1/index/', methods=['GET'])
def get_index():
    """show_index."""
    # pr.enable()
    q = re.sub(r'[^a-zA-Z ]+', '', flask.request.args["q"]).lower()
    p = int(flask.request.args["p"])
    dindex = recipe_index.app.config["INDEX_DICT"]

    vectors = {}
    q_list = q.split()
    q_len = len(q_list)
    q_vector = [0] * q_len
    for i, term in enumerate(q_list):
        if term not in dindex:
            continue
        row = dindex[term]
        idf = row[0]
        values = row[1:]

        q_vector[i] = q_list.count(term) * float(idf)

        for j in range(0, len(values), 2):
            docid, tfidf = values[j:j+2]

            if docid in vectors:
                vectors[docid][i] = tfidf
            else:
                vectors[docid] = [0] * q_len
                vectors[docid][i] = tfidf
    
        
    q_vector_sum_squares = 0
    for i,v in enumerate(q_vector):
        q_vector_sum_squares += math.pow(v, 2)
    for i,v in enumerate(q_vector):
        q_vector[i] /= math.sqrt(q_vector_sum_squares)
    
    filtered_vectors = {k:v for (k,v) in vectors.items() if 0 not in v}
    docs = []
    for docid, tfidf in filtered_vectors.items():
        score = 0
        for i in range(0, q_len):
            score += q_vector[i] * tfidf[i]
        docs.append({
            "docid": docid,
            "score": score
        })
    
    docs = sorted(docs, key=lambda doc: doc["score"], reverse=True)
    # pr.disable()
    # s = io.StringIO()
    # sortby = SortKey.CUMULATIVE
    # ps = pstats.Stats(pr, stream=s).sort_stats(sortby)
    # ps.print_stats()
    # print(s.getvalue())
    return(
        flask.make_response(
            flask.jsonify({"docs": docs[10*(p-1):10*p], "length": len(docs)})
        )
    )
