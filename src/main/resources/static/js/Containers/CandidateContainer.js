var CandidateContainer = React.createClass({

  getInitialState: function() {
    return {
      candidateList : [],
      isLoading : true,
    };
  },

  componentWillMount: function() {
    var self = this;
    axios
      .get('/candidate')
      .then(function(response){
        self.setState({
          candidateList :  response.data,
          isLoading : false,
        });
      })
      .catch(function(err){
        console.error('CandidateContainer.componentWillMount.axios.get.candidate', err);
      });
  },

  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='https://upload.wikimedia.org/wikipedia/commons/b/b1/Loading_icon.gif'/>
        </div>
      );
    } else {
      console.log(this.state.candidateList);
      return (
        <CandidatesListViewComponent candidateList={this.state.candidateList}/>
      );
    }

  }

});

window.CandidateContainer = CandidateContainer;
