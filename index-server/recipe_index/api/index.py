import flask
import math
import recipe_index

@recipe_index.app.route('/api/v1/index/', methods=['GET'])
def get_index():
    """show_index."""
    q = flask.request.args["q"]
    p = int(flask.request.args["p"])
    dindex = recipe_index.app.config["INDEX_DICT"]

    vectors = {}
    q_list = q.split()
    q_vector = [0] * len(q_list)
    for i, term in enumerate(q_list):
        if term not in dindex:
            continue
        dindex = recipe_index.app.config["INDEX_DICT"]
        values = dindex[term]

        for j in range(0, len(values), 4):
            idf, docid, doctf, norm = values[j:j+4]

            if (q_vector[i] == 0):
                qtfidf = q_list.count(term) * float(idf)
                q_vector[i] = qtfidf

            tfidf = float(doctf)*float(idf)/math.sqrt(float(norm))

            if docid in vectors:
                vectors[docid][i] = tfidf
            else:
                vectors[docid] = [0] * len(q.split())
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
        for i in range(0, len(q_vector)):
            score += q_vector[i] * tfidf[i]
        docs.append({
            "docid": docid,
            "score": score
        })
    
    docs = sorted(docs, key=lambda doc: doc["score"], reverse=True)
        
    return(
        flask.make_response(
            flask.jsonify({"docs": docs[10*(p-1):10*p]})
        )
    )
