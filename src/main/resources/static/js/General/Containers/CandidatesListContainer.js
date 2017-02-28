var CandidatesListContainer = React.createClass({

  getInitialState: function() {
    return {
      candidateList : [],
      isLoading : true,
      succesCreateText: '',
      fade : {backgroundColor : 'yellow'},
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
        console.error('CandidatesListContainer.componentWillMount.axios.get.candidate', err);
      });
  },
  componentDidMount: function() {
    this.setState({
      fade : {backgroundColor : 'green'},
    });
  },
  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='./Images/loading.gif'/>
        </div>
      );
    } else {
      return (
        <CandidatesList
          candidateList={this.state.candidateList}
          succesCreateText={this.state.succesCreateText}
          fade={this.state.fade}
        />
      );
    }

  }

});

window.CandidatesListContainer = CandidatesListContainer;
